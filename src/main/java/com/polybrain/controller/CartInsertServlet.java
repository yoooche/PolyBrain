package com.polybrain.controller;

import com.polybrain.model.CartTraceTO;
import com.polybrain.model.Core;
import com.polybrain.service.CartTraceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.polybrain.util.CommonUtil.json2Pojo;
import static com.polybrain.util.CommonUtil.writePojo2Json;
import static com.polybrain.util.Constants.SESSION_MEM_NO;

@WebServlet("/loginRequired/CartInsert")
public class CartInsertServlet extends HttpServlet {
    private final CartTraceService cartTraceService = new CartTraceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Integer memNo = (Integer) req.getSession().getAttribute(SESSION_MEM_NO);
        // 沒登入時 LoginFilter 已經回 401，前端（search.js 等）看到 401 會自己導去登入頁
        if (memNo == null) {
            return;
        }

        CartTraceTO cartTraceVO = json2Pojo(req, CartTraceTO.class);
        cartTraceVO.setMemNo(memNo);
        Core core = new Core();
        if (cartTraceVO.getItemNo() == null) {
            core.setMessage("未有參數");
            core.setSuccess(false);
        } else {
            core.setSuccess(cartTraceService.addToCart(cartTraceVO));
        }
        writePojo2Json(res, core);
    }
}
