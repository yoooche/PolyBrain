package com.polybrain.controller;

import com.polybrain.service.LoginService;
import com.polybrain.service.impl.LoginServiceImpl;
import com.polybrain.model.MemTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.polybrain.util.CommonUtil.json2Pojo;
import static com.polybrain.util.CommonUtil.writePojo2Json;

@WebServlet("/general/login")
public class MemLoginController extends HttpServlet {
    private LoginService loginService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ok");
        MemTO memVo = json2Pojo(req, MemTO.class);
        loginService = new LoginServiceImpl();
        boolean validate = loginService.validateInfo(memVo.getMemEmail(), memVo.getMemPwd());
        memVo.setSuccess(validate);
        if(validate){
            HttpSession session = req.getSession();
            String sessionId = session.getId();
            Integer memNo = loginService.getMemByValidatedInfo(memVo.getMemEmail(), memVo.getMemPwd()).getMemNo();
            System.out.println("登入成功，會員編號:" + memNo + "sessionId" + sessionId);

            session.setAttribute("sessionId", sessionId);
            session.setAttribute("memNo", memNo);

            writePojo2Json(resp, memVo);
        }else{
            System.out.println("登入失敗");
            writePojo2Json(resp, memVo);
        }
    }
}
