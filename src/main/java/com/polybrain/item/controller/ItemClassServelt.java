package com.polybrain.item.controller;

import com.polybrain.common.util.CommonUtil;
import com.polybrain.item.service.*;
import com.polybrain.item.service.impl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/general/item/ItemClass")
public class ItemClassServelt extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ItemClassService service = new ItemClassServiceImpl();
    CommonUtil commonUtil = new CommonUtil();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
            commonUtil.writePojo2Json(response, service.getAllClasses());
    }
}
