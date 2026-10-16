package com.polybrain.booking.controller;

import com.polybrain.booking.entity.BookingVo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.polybrain.common.util.CommonUtil.writePojo2Json;
import static com.polybrain.booking.util.BookConstants.SERVICE;
import static com.polybrain.booking.util.BookConstants.SERVICETable;

@WebServlet("/loginRequired/book/checkstate")
public class BookCheckStateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        String bookNoParam = request.getParameter("bookingNo");
        String checkStateParam = request.getParameter("newState");
        System.out.println("單號:" + bookNoParam);
        System.out.println("報到:" + checkStateParam);
        Integer checkState = Integer.parseInt(String.valueOf(checkStateParam));
        Integer bookNo = Integer.parseInt(String.valueOf(bookNoParam));
        BookingVo change = SERVICE.checkBookState(bookNo, checkState);
        writePojo2Json(response, change);
    }
}
