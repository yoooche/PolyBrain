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

import static com.polybrain.util.CommonUtil.writePojo2Json;
import static com.polybrain.util.Constants.SESSION_MEM_NO;

/** 會員查自己某一筆商城訂單的明細（memberOrder.html） */
@WebServlet("/loginRequired/findAllOrderDetail")
public class MemberOrderDetail extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(MemberOrderDetail.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer memNo = (Integer) req.getSession().getAttribute(SESSION_MEM_NO);
        Integer orderNo = Integer.valueOf(req.getParameter("orderNo"));
        OrderService orderService = new OrderService();

        ItemOrderTO itemOrderVO = orderService.getOneOrder(orderNo);
        if (itemOrderVO == null || !itemOrderVO.getMemNo().equals(memNo)) {
            logger.warn("Member {} requested order {} that is not theirs", memNo, orderNo);
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        writePojo2Json(res, orderService.selectOrderDetail(orderNo));
    }
}
