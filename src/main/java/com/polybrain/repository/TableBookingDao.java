package com.polybrain.repository;


import com.polybrain.repository.CoreDao;
import com.polybrain.model.TableBookingTO;
import org.jetbrains.annotations.NotNull;


import java.sql.Date;
import java.util.List;

public interface TableBookingDao extends CoreDao<TableBookingTO, Integer> {


    Integer updateById(@NotNull TableBookingTO newtablebook);

    //選全部
    List<TableBookingTO> selectAll();

    public List<TableBookingTO> SelectByChoose(int tableno);
    public TableBookingTO updateByState(Integer stateNo,Date bookDate,Integer tableNo);
    public TableBookingTO cancelByState(Integer stateNo,Date bookDate,Integer tableNo);


}
