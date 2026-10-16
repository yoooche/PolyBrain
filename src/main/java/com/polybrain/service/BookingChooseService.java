package com.polybrain.service;

import com.polybrain.service.CoreService;
import com.polybrain.model.TableBookingTO;


import java.sql.Date;
import java.util.List;

public interface BookingChooseService extends CoreService {
    List<TableBookingTO> selectByDate(int tableno);
    //更新狀態
    TableBookingTO selectByState(Integer stateNo,Date bookDate,Integer tableNo);
    TableBookingTO selectCancelState(Integer stateNo,Date bookDate,Integer tableNo);
}
