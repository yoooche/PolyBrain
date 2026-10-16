package com.polybrain.service.impl;

import com.polybrain.repository.TableBookingDao;
import com.polybrain.repository.impl.TableBookingImpl;
import com.polybrain.model.TableBookingTO;
import org.hibernate.SessionFactory;


import java.sql.Date;
import java.util.List;
import com.polybrain.service.BookingChooseService;

public class BookingChooSerImpl implements BookingChooseService{
    private SessionFactory sessionFactory;
    private TableBookingDao Dao;
    public BookingChooSerImpl(){
        Dao = new TableBookingImpl();
    }
    @Override
    public List<TableBookingTO> selectByDate(int tableno){
//        beginTransaction();
        List<TableBookingTO> result = Dao.SelectByChoose(tableno);
//        commit();
        return result;
    }
    //改變狀態
    public TableBookingTO selectByState(Integer stateNo,Date tabledate,Integer tableNo){
//        beginTransaction();
        TableBookingTO result = Dao.updateByState(stateNo, tabledate, tableNo);
//        commit();
        return result;
        //return Dao.updateByState(stateNo, tabledate, tableNo);
    }
    //取消狀態，返還時段
    public TableBookingTO selectCancelState(Integer stateNo,Date bookDate,Integer tableNo){
//        beginTransaction();
        TableBookingTO result = Dao.cancelByState(stateNo,bookDate,tableNo);
//        commit();
        return result;
        //return Dao.cancelByState(stateNo,bookDate,tableNo);
    }

}
