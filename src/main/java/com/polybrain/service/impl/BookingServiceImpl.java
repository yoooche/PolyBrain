package com.polybrain.service.impl;

import com.polybrain.repository.BookingDao;
import com.polybrain.repository.impl.BookingDaoImpl;
import com.polybrain.model.BookingTO;
import org.hibernate.SessionFactory;

import java.sql.Date;
import java.util.List;
import com.polybrain.service.BookingService;

public class BookingServiceImpl implements BookingService {
    private SessionFactory sessionFactory;

    private BookingDao Dao;
    public BookingServiceImpl(){
        Dao = new BookingDaoImpl();
    }
    //用預約狀態以及日期條件查詢
    @Override
    public List<BookingTO> selectdate(int state, Date startDate, Date endDate, Integer bookingNo, Integer memNo){
        List<BookingTO> result = Dao.selectByDate(state, startDate, endDate, bookingNo, memNo);
        return result;
    }
    @Override
    public List<BookingTO> getAllBooking(){
        List<BookingTO> result = Dao.selectAll();
        return  result;
    }
    //已預約狀態查詢(暫不用)
    @Override
    public List<BookingTO> selectone(Integer state) {
//        if(state == 1){
//            return  Dao.selectById(1);
//        } else if (state == 0) {
//            return  Dao.selectById(0);
//        }else if (state == 2) {
//            return Dao.selectAll();
//        }
        return null;
    }

    @Override
    public BookingTO cancelBooking(Integer bookno, Integer newState){
//        beginTransaction();
        BookingTO result = Dao.cancel(bookno, newState);
//        commit();
        return result;
        //return Dao.cancel(bookno, newState);
    }
    @Override
    public BookingTO checkBookState(Integer bookno, Integer newState){
//        beginTransaction();
        BookingTO result = Dao.checkState(bookno, newState);
//        commit();
        return result;
        //return Dao.checkState(bookno, newState);
    }

    @Override
    public boolean remove(Integer bookingno) {
        return Dao.deletbyId(bookingno) > 0;
    }

    @Override
    public boolean save(BookingTO booking) {
        return Dao.updateById(booking) > 0;
    }
    //新的
    public BookingTO insert (BookingTO bookingVo){

        final BookingTO result = Dao.insert(bookingVo);
        final Integer result1 = Dao.selectByIdMem(bookingVo.getBookingno());

        bookingVo.setMessage("ok");
        bookingVo.setSuccess(true);
        System.out.println("Service:" + bookingVo);
        System.out.println("Service:" + result);

        Integer result2 = bookingVo.getBookingno();
        //System.out.println("會員編號" + result1);
        return bookingVo;
    }

    @Override
    public BookingTO selectById(Integer memno) {
        return null;
    }
}

