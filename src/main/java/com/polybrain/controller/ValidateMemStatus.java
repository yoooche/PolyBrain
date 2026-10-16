package com.polybrain.controller;

import com.polybrain.dto.LoginStatusDto;
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

import static com.polybrain.util.CommonUtil.writePojo2Json;

@WebServlet("/general/validateMemStatus")
public class ValidateMemStatus extends HttpServlet {
    private LoginService loginService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("UTF-8");
        HttpSession session = req.getSession();

        LoginStatusDto loginStatusDto = new LoginStatusDto();
        if(session.getAttribute("sessionId") != null){
            loginService = new LoginServiceImpl();
            MemTO memVo = loginService.getMemByMemNo((Integer) session.getAttribute("memNo"));
            loginStatusDto.setMemNo(memVo.getMemNo());
            loginStatusDto.setMemName(memVo.getMemName());
            loginStatusDto.setLoginStatus(true);
            writePojo2Json(resp, loginStatusDto);
        }else{
            loginStatusDto.setLoginStatus(false);
            writePojo2Json(resp, loginStatusDto);
        }
    }
}
