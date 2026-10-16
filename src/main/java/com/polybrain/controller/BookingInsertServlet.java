package com.polybrain.controller;

import com.polybrain.util.CommonUtil;
import com.polybrain.service.BookingService;
import com.polybrain.service.impl.BookingServiceImpl;
import com.polybrain.model.BookingTO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.polybrain.service.BookMailServ;
import static com.polybrain.util.CommonUtil.*;
import static com.polybrain.util.Constants.JSON_MIME_TYPE;

@WebServlet("/loginRequired/booking/insert")
public class BookingInsertServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingService service = new BookingServiceImpl();
    CommonUtil commonUtil = new CommonUtil();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setContentType(JSON_MIME_TYPE);
        BookingTO bookingVo = json2Pojo(request, BookingTO.class);

        System.out.println(request.getRequestURI());
        System.out.println("bookingVo:" + bookingVo);

        //insert
        commonUtil.writePojo2Json(response, service.insert(bookingVo));
        Integer number = bookingVo.getBookingno();

        Integer mem = bookingVo.getMemno();
//        System.out.println("number" + number);
//        System.out.println("mem" + mem);
        //BookingTO memno = service.selectById(mem);
        BookMailServ bookMailServ = new BookMailServ();
        bookMailServ.sendMail(number);

    }
}
