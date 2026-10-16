package com.polybrain.member.controller;

import com.polybrain.member.dto.LoginStatusDto;
import com.polybrain.member.service.LoginService;
import com.polybrain.member.service.impl.LoginServiceImpl;
import com.polybrain.member.entity.MemVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.polybrain.common.util.CommonUtil.writePojo2Json;

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
            MemVo memVo = loginService.getMemByMemNo((Integer) session.getAttribute("memNo"));
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
