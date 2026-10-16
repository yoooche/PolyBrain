package com.polybrain.repository;

import com.polybrain.repository.CoreDao;
import com.polybrain.model.BookingTO;

import java.sql.Date;
import java.util.List;

public interface BookingDao extends CoreDao{

    BookingTO insert(BookingTO book);

    Integer deletbyId(Integer bookingno);
    List<BookingTO> selectAll();

    //Table booking 只會有state狀態改變
    Integer updateById(BookingTO newbook);

//    List<BookingTO> selectById(Integer bookingno);

    //查一個
    BookingTO selectById(Integer bookingno);
    //獲取對應的會員編號，以利發信
    Integer selectByIdMem(Integer bookingno);

    //查日期
    public List<BookingTO> selectByDate(int state, Date startDate, Date endDate, Integer bookingNo,Integer memNo);

    //取消
//    Integer cancel (Integer bookno);

    BookingTO cancel(Integer bookingNo, Integer newState);
    BookingTO checkState(Integer bookingNo, Integer newState);
}
