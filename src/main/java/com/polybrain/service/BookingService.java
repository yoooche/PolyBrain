package com.polybrain.service;

import com.polybrain.service.CoreService;
import com.polybrain.model.BookingTO;

import java.sql.Date;
import java.util.List;

public interface BookingService extends CoreService {
    //查全部
    List<BookingTO> getAllBooking();

    List<BookingTO> selectone(Integer bookingState);
    List<BookingTO> selectdate(int state, Date startDate, Date endDate, Integer bookingNo, Integer memNo);

    BookingTO cancelBooking(Integer bookno, Integer newState);
    BookingTO checkBookState(Integer bookno, Integer newState);

    boolean remove(Integer bookingno);

    boolean save(BookingTO booking);

    //新的
    BookingTO insert(BookingTO bookingVo);
    BookingTO selectById(Integer memno);


}
