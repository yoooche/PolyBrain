package com.polybrain.controller;

import com.polybrain.model.Core;
import com.polybrain.util.CommonUtil;
import com.polybrain.service.CartTraceService;
import com.polybrain.model.CartTraceTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.polybrain.util.CommonUtil.json2Pojo;

@WebServlet("/loginRequired/CartInsert")
public class CartInsertServlet extends HttpServlet {
    CommonUtil commonUtil = new CommonUtil();
    CartTraceService cartTraceService = new CartTraceService();
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        final CartTraceTO cartTraceVO = json2Pojo(req, CartTraceTO.class);
        HttpSession session = req.getSession();
        Integer memNo = (Integer) session.getAttribute("memNo");
        cartTraceVO.setMemNo(memNo);
        final Core core = new Core();
        if (cartTraceVO.getItemNo() == null) {
            core.setMessage("未有參數");
            core.setSuccess(false);
        } else {
            core.setSuccess(cartTraceService.addToCart(cartTraceVO));
        }
        commonUtil.writePojo2Json(res, core);
    }
}
