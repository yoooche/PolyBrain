package com.polybrain.controller;

import com.polybrain.util.CommonUtil;
import com.polybrain.service.OrderService;
import com.polybrain.model.ItemOrderTO;
import com.polybrain.dto.OrderDetailDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@WebServlet("/loginRequired/findAllOrderDetail")
public class MemberOrderDetail extends HttpServlet {
    private static final long serialVersionUID = 1L;
    CommonUtil commonUtil = new CommonUtil();
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer orderNo = Integer.valueOf(req.getParameter("orderNo"));
        req.setCharacterEncoding("UTF-8");
        List<OrderDetailDTO> itemOrderDetailVOList = new OrderService().selectOrderDetail(orderNo);
        System.out.println(itemOrderDetailVOList);
        commonUtil.writePojo2Json(res, itemOrderDetailVOList);

    }

}
