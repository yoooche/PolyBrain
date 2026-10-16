package com.polybrain.booking.service;

import com.polybrain.common.service.CoreService;
import com.polybrain.booking.entity.TableBookingVo;


import java.sql.Date;
import java.util.List;

public interface BookingChooseService extends CoreService {
    List<TableBookingVo> selectByDate(int tableno);
    //更新狀態
    TableBookingVo selectByState(Integer stateNo,Date bookDate,Integer tableNo);
    TableBookingVo selectCancelState(Integer stateNo,Date bookDate,Integer tableNo);
}
