package com.polybrain.controller;

import com.polybrain.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.polybrain.util.CommonUtil.writePojo2Json;
import static com.polybrain.util.Constants.SESSION_MEM_NO;

/** 會員自己的商城訂單列表（memberOrder.html、orderSuccess.html） */
@WebServlet("/loginRequired/findAllOrder")
public class MemberOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer memNo = (Integer) req.getSession().getAttribute(SESSION_MEM_NO);
        writePojo2Json(res, new OrderService().selectFromMemNo(memNo));
    }
}
