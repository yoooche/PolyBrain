package com.polybrain.controller;

import com.polybrain.dto.CartItemImgDTO;
import com.polybrain.model.CartTraceTO;
import com.polybrain.service.CartTraceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.polybrain.util.CommonUtil.writePojo2Json;
import static com.polybrain.util.Constants.LOGIN_PAGE;
import static com.polybrain.util.Constants.SESSION_MEM_NO;

@WebServlet("/loginRequired/CartServlet")
public class CartServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(CartServlet.class);
    private static final String CART_PAGE = "/view/CartTrace/Cart.jsp";
    private static final String CART_URL = "/loginRequired/CartServlet?action=getAll";
    private static final String SESSION_CART_ITEMS = "cartItemImgDTOList";

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
        // 導覽列的購物車是一般表單送出，LoginFilter 回的 401 瀏覽器只會顯示空白頁，所以直接導去登入
        if (memNo == null) {
            res.sendRedirect(req.getContextPath() + LOGIN_PAGE);
            return;
        }

        String action = req.getParameter("action");
        if ("getAll".equals(action)) {
            // 導覽列是用 POST 表單進來的；直接顯示的話，從結帳頁按上一頁會變成「確認重新提交表單」。
            // 改導到 GET 網址，上一頁或重新整理都只是重新查一次購物車
            if ("POST".equals(req.getMethod())) {
                res.sendRedirect(req.getContextPath() + CART_URL);
                return;
            }
            List<CartItemImgDTO> cartItems = cartTraceService.getAllCartItem(memNo);
            session.setAttribute(SESSION_CART_ITEMS, cartItems);
            req.getRequestDispatcher(CART_PAGE).forward(req, res);
        }

        if ("UpdateItemQuantity".equals(action)) {
            Integer itemNo = Integer.valueOf(req.getParameter("itemNo"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            int deleteARow = Integer.parseInt(req.getParameter("deleteARow"));

            if (quantity == 0 || deleteARow == 0) {
                cartTraceService.deleteByMemItemNo(memNo, itemNo);
                logger.info("Member {} removed item {} from cart", memNo, itemNo);
            } else if (quantity > 0) {
                cartTraceService.updateArow(new CartTraceTO(memNo, itemNo, quantity));
                logger.info("Member {} set cart item {} quantity to {}", memNo, itemNo, quantity);
            }
            writePojo2Json(res, Collections.singletonMap("message", "success"));
        }
    }
}
