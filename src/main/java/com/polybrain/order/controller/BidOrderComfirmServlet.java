package com.polybrain.order.controller;

import com.polybrain.common.util.CommonUtil;
import com.polybrain.bid.entity.BidOrderVo;
import com.polybrain.order.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loginRequired/bidOrderConfirm")
public class BidOrderComfirmServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    CommonUtil commonUtil = new CommonUtil();
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setCharacterEncoding("UTF-8");
        Integer orderNo = Integer.valueOf(req.getParameter("orderNo"));
        System.out.println("orderNo: " + orderNo);

        BidOrderVo bidOrderVo = new OrderService().getBidOneOrder(orderNo);

        System.out.println("bidOrderVo: "+bidOrderVo);
        commonUtil.writePojo2Json(res, bidOrderVo);


    }

}



