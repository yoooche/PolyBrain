package com.polybrain.controller;

import com.polybrain.model.BookingTO;
import com.polybrain.model.MemTO;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


import static com.polybrain.util.CommonUtil.writePojo2Json;
import static com.polybrain.util.Constants.JSON_MIME_TYPE;
import static com.polybrain.util.BookConstants.SERVICE;


@WebServlet("/loginRequired/booking/list")
public class BookListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType(JSON_MIME_TYPE);
        List<BookingTO> booklist = SERVICE.getAllBooking();
        for (BookingTO bookingVo : booklist){
            MemTO memVo = bookingVo.getMemvo();
            String memPh = memVo.getMemPh();
            System.out.println("Phone" + memPh);
            bookingVo.setMemPh(memPh);
        }
        writePojo2Json(response,booklist);
    }
}
