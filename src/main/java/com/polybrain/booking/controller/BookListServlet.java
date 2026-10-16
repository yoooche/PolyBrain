package com.polybrain.booking.controller;

import com.polybrain.booking.entity.BookingVo;
import com.polybrain.member.entity.MemVo;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


import static com.polybrain.common.util.CommonUtil.writePojo2Json;
import static com.polybrain.common.util.Constants.JSON_MIME_TYPE;
import static com.polybrain.booking.util.BookConstants.SERVICE;


@WebServlet("/loginRequired/booking/list")
public class BookListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType(JSON_MIME_TYPE);
        List<BookingVo> booklist = SERVICE.getAllBooking();
        for (BookingVo bookingVo : booklist){
            MemVo memVo = bookingVo.getMemvo();
            String memPh = memVo.getMemPh();
            System.out.println("Phone" + memPh);
            bookingVo.setMemPh(memPh);
        }
        writePojo2Json(response,booklist);
    }
}
