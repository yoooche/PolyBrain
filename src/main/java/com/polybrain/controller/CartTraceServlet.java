package com.polybrain.controller;

import com.polybrain.dto.CartItemImgDTO;
import com.polybrain.dto.CheckoutReceiverDTO;
import com.polybrain.model.MemTO;
import com.polybrain.service.CartTraceService;
import com.polybrain.service.MailService;
import com.polybrain.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.polybrain.util.CommonUtil.trimToEmpty;
import static com.polybrain.util.Constants.LOGIN_PAGE;
import static com.polybrain.util.Constants.SESSION_MEM_NO;
import static com.polybrain.util.HibernateUtil.runAfterCommit;
import static com.polybrain.util.OrderUtil.ORDER_STATE_NEW;
import static com.polybrain.util.OrderUtil.calcOrderTotal;
import static com.polybrain.util.OrderUtil.findOutOfStock;
import static com.polybrain.util.OrderUtil.toReceiverMethod;
import static com.polybrain.util.OrderUtil.validateReceiver;

/**
 * 結帳流程：Cart.jsp →（getAll）→ CartTrace.jsp 填收件資料 →（preview）→ OrderConfirm.jsp 確認 →（placeOrder）→ 成立訂單
 */
@WebServlet("/loginRequired/ConfirmOrder")
public class CartTraceServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(CartTraceServlet.class);
    private static final String CHECKOUT_PAGE = "/view/CartTrace/CartTrace.jsp";
    private static final String ORDER_CONFIRM_PAGE = "/view/CartTrace/OrderConfirm.jsp";
    private static final String ORDER_SUCCESS_PAGE = "/view/order/orderSuccess.html?orderNo=";
    private static final String SESSION_CART_ITEMS = "cartItemImgDTOList";
    private static final String SESSION_RECEIVER = "checkoutReceiver";

    private final CartTraceService cartTraceService = new CartTraceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        Integer memNo = (Integer) session.getAttribute(SESSION_MEM_NO);
        if (memNo == null) {
            res.sendRedirect(req.getContextPath() + LOGIN_PAGE);
            return;
        }

        String action = req.getParameter("action");
        // Cart.jsp 進結帳頁前用 fetch 呼叫，只是把購物車放進 session 給 CartTrace.jsp 顯示
        if ("getAll".equals(action)) {
            session.setAttribute(SESSION_CART_ITEMS, cartTraceService.getAllCartItem(memNo));
        }
        if ("preview".equals(action)) {
            preview(req, res, session, memNo);
        }
        if ("placeOrder".equals(action)) {
            placeOrder(req, res, session, memNo);
        }
    }

    /** 檢查收件資料和購物車，沒問題就把收件資料存進 session，帶去確認頁；這一步不會寫入任何訂單 */
    private void preview(HttpServletRequest req, HttpServletResponse res, HttpSession session, Integer memNo)
            throws ServletException, IOException {
        CheckoutReceiverDTO receiver = readReceiver(req);
        Map<String, String> errorMsgs = validateReceiver(receiver);

        List<CartItemImgDTO> cartItems = cartTraceService.getAllCartItem(memNo);
        session.setAttribute(SESSION_CART_ITEMS, cartItems);
        String cartError = checkCart(cartItems);
        if (cartError != null) {
            errorMsgs.put("noConfirmOrder", cartError);
        }

        if (!errorMsgs.isEmpty()) {
            req.setAttribute("errorMsgs", errorMsgs);
            req.setAttribute(SESSION_RECEIVER, receiver); // 帶回剛才填的值
            req.getRequestDispatcher(CHECKOUT_PAGE).forward(req, res);
            return;
        }

        receiver.setPreviewTotal(calcOrderTotal(cartItems));
        session.setAttribute(SESSION_RECEIVER, receiver);
        res.sendRedirect(req.getContextPath() + ORDER_CONFIRM_PAGE);
    }

    /** 確認頁按「確認下單」：鎖住會員和庫存，重新檢查一次，金額和確認頁一樣才成立訂單 */
    private void placeOrder(HttpServletRequest req, HttpServletResponse res, HttpSession session, Integer memNo)
            throws ServletException, IOException {
        CheckoutReceiverDTO receiver = (CheckoutReceiverDTO) session.getAttribute(SESSION_RECEIVER);
        if (receiver == null) { // 沒經過確認頁（例如已經下單過、session 過期）
            res.sendRedirect(req.getContextPath() + CHECKOUT_PAGE);
            return;
        }

        OrderService orderService = new OrderService();
        // 先鎖會員再讀購物車：連按兩次確認時，第二次會等第一次 commit（購物車已清空、收件資料已移除）
        MemTO member = orderService.lockMember(memNo);
        List<CartItemImgDTO> cartItems = cartTraceService.getAllCartItem(memNo);
        // 兩個會員同時買最後一件時，後到的會等先到的 commit，拿到扣完的庫存
        orderService.lockStock(cartItems);
        session.setAttribute(SESSION_CART_ITEMS, cartItems);

        String cartError = checkCart(cartItems);
        int orderTotal = calcOrderTotal(cartItems);
        // 確認頁顯示之後，價格或購物車在別的分頁被改了：不能用會員沒看過的金額下單
        if (cartError == null && orderTotal != receiver.getPreviewTotal()) {
            cartError = "購物車內容或金額有變動，請再確認一次";
            receiver.setPreviewTotal(orderTotal);
        }
        if (cartError != null) {
            req.setAttribute("errorMsg", cartError);
            req.getRequestDispatcher(ORDER_CONFIRM_PAGE).forward(req, res);
            return;
        }

        Integer orderNo = orderService.addOrder(memNo, orderTotal, ORDER_STATE_NEW, receiver.getReceiverName(),
                receiver.getFullAddress(), receiver.getReceiverPhone(), toReceiverMethod(receiver.getReceiverMethod()));
        orderService.addAnOrderDetail(cartItems, orderNo, memNo);
        session.removeAttribute(SESSION_CART_ITEMS);
        session.removeAttribute(SESSION_RECEIVER);
        logger.info("Member {} placed order {}, total {}", memNo, orderNo, orderTotal);

        // 寄信要好幾秒，放到 commit 之後：不必拿著會員和庫存的鎖等寄信，commit 失敗也不會寄出確認信
        String memName = member.getMemName();
        String memEmail = member.getMemEmail();
        runAfterCommit(() -> new MailService().sendOrderMail(memName, memEmail, orderNo, orderTotal));

        // 用 redirect：重新整理成功頁不會再送一次訂單
        res.sendRedirect(req.getContextPath() + ORDER_SUCCESS_PAGE + orderNo);
    }

    private static CheckoutReceiverDTO readReceiver(HttpServletRequest req) {
        CheckoutReceiverDTO receiver = new CheckoutReceiverDTO();
        receiver.setReceiverName(trimToEmpty(req.getParameter("receiverName")));
        receiver.setReceiverPhone(trimToEmpty(req.getParameter("receiverPhone")));
        receiver.setCounty(trimToEmpty(req.getParameter("county")));
        receiver.setDistrict(trimToEmpty(req.getParameter("district")));
        receiver.setReceiverAddress(trimToEmpty(req.getParameter("receiverAddress")));
        receiver.setReceiverMethod(trimToEmpty(req.getParameter("receiverMethod")));
        return receiver;
    }

    /** 購物車是空的或庫存不夠就回錯誤訊息，沒問題回 null */
    private static String checkCart(List<CartItemImgDTO> cartItems) {
        if (cartItems.isEmpty()) {
            return "購物車是空的";
        }
        CartItemImgDTO outOfStock = findOutOfStock(cartItems);
        if (outOfStock != null) {
            return outOfStock.getItemName() + " 庫存只剩 " + outOfStock.getItemQty() + " 個";
        }
        return null;
    }
}
