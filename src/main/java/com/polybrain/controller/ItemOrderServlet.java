package com.polybrain.controller;

import com.polybrain.model.ItemOrderTO;
import com.polybrain.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.polybrain.util.CommonUtil.trimToEmpty;
import static com.polybrain.util.OrderUtil.isValidPhone;

/** 後台訂單管理（listAllOrder.jsp、updateOrder_input.jsp） */
@WebServlet("/view/order/order.tw")
public class ItemOrderServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ItemOrderServlet.class);
    private static final String ORDER_LIST_PAGE = "/view/order/listAllOrder.jsp";
    private static final String ORDER_EDIT_PAGE = "/view/order/updateOrder_input.jsp";
    private static final String RECEIVER_NAME_REGEX = "^[(一-龥)(a-zA-Z0-9-)]{2,10}$";
    private static final int MAX_ADDRESS_LENGTH = 255; // item_order.RECEIVER_ADDRESS 的欄位長度

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("test1");

        if ("getOne_For_Update".equals(action)) {
            Integer orderNo = Integer.valueOf(trimToEmpty(req.getParameter("orderNo")));
            ItemOrderTO itemOrderVO = orderService.getOneOrder(orderNo);
            if (itemOrderVO == null) {
                return;
            }
            req.setAttribute("errorMsgs", new LinkedList<String>());
            req.setAttribute("itemorder", itemOrderVO);
            req.getRequestDispatcher(ORDER_EDIT_PAGE).forward(req, res);
        }

        if ("update".equals(action)) {
            updateReceiver(req, res);
        }

        if ("delete".equals(action)) {
            Integer orderNo = Integer.valueOf(req.getParameter("orderNo"));
            orderService.deleteById(orderNo);
            logger.info("Back office deleted order {}", orderNo);
            res.sendRedirect(req.getContextPath() + ORDER_LIST_PAGE);
        }
    }

    private void updateReceiver(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer orderNo = Integer.valueOf(req.getParameter("orderNo"));
        List<String> errorMsgs = new LinkedList<>();

        String receiverName = trimToEmpty(req.getParameter("receiverName"));
        if (receiverName.isEmpty()) {
            errorMsgs.add("收件人姓名請勿空白");
        } else if (!receiverName.matches(RECEIVER_NAME_REGEX)) {
            errorMsgs.add("收件人姓名只能是中英文,數字,- 且長度介於2-10之間");
        }

        String receiverAddress = trimToEmpty(req.getParameter("receiverAddress"));
        if (receiverAddress.isEmpty()) {
            errorMsgs.add("收件地址不為空");
        } else if (receiverAddress.length() > MAX_ADDRESS_LENGTH) {
            errorMsgs.add("收件地址太長");
        }

        String receiverPhone = trimToEmpty(req.getParameter("receiverPhone"));
        if (receiverPhone.isEmpty()) {
            errorMsgs.add("收件人電話不為空");
        } else if (!isValidPhone(receiverPhone)) {
            errorMsgs.add("手機號碼格式不正確");
        }

        if (!errorMsgs.isEmpty()) {
            // 另外 new 一個只給畫面顯示；改到查出來的訂單的話，transaction commit 時會把錯的資料存進去
            ItemOrderTO formValues = new ItemOrderTO();
            formValues.setOrderNo(orderNo);
            formValues.setMemNo(Integer.valueOf(req.getParameter("memNo")));
            formValues.setReceiverName(receiverName);
            formValues.setReceiverAddress(receiverAddress);
            formValues.setReceiverPhone(receiverPhone);
            req.setAttribute("errorMsgs", errorMsgs);
            req.setAttribute("itemorder", formValues);
            req.getRequestDispatcher(ORDER_EDIT_PAGE).forward(req, res);
            return;
        }

        // 在查出來的訂單上改，其他欄位（狀態、寄送方式、成立日期）才不會被蓋成 null
        ItemOrderTO itemOrderVO = orderService.getOneOrder(orderNo);
        itemOrderVO.setReceiverName(receiverName);
        itemOrderVO.setReceiverAddress(receiverAddress);
        itemOrderVO.setReceiverPhone(receiverPhone);
        orderService.updateBackOrder(itemOrderVO);
        logger.info("Back office updated receiver info of order {}", orderNo);
        res.sendRedirect(req.getContextPath() + ORDER_LIST_PAGE);
    }
}
