package com.polybrain.booking.dao;


import com.polybrain.common.dao.CoreDao;
import com.polybrain.booking.entity.TableBookingVo;
import org.jetbrains.annotations.NotNull;


import java.sql.Date;
import java.util.List;

public interface TableBookingDao extends CoreDao<TableBookingVo, Integer> {


    Integer updateById(@NotNull TableBookingVo newtablebook);

    //選全部
    List<TableBookingVo> selectAll();

    public List<TableBookingVo> SelectByChoose(int tableno);
    public TableBookingVo updateByState(Integer stateNo,Date bookDate,Integer tableNo);
    public TableBookingVo cancelByState(Integer stateNo,Date bookDate,Integer tableNo);


}
