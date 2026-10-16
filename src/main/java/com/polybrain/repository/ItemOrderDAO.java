package com.polybrain.repository;

import com.polybrain.repository.CoreDao;
import com.polybrain.model.ItemOrderDetailTO;
import com.polybrain.model.ItemOrderTO;

import java.util.List;

public interface ItemOrderDAO extends CoreDao<ItemOrderTO, Integer>{


    List<ItemOrderDetailTO> getDetailByOrderNumber(Integer orderNo);

    List<ItemOrderTO> selectByMemberNumber(Integer memNo);

    ItemOrderTO updateAnOrder(ItemOrderTO itemordervo);

}
