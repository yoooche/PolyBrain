package com.polybrain.repository;

import com.polybrain.model.ItemOrderDetailTO;
import com.polybrain.model.ItemOrderTO;

import java.util.List;

public interface ItemOrderDAO extends CoreDao<ItemOrderTO, Integer> {
    List<ItemOrderDetailTO> getDetailByOrderNumber(Integer orderNo);

    List<ItemOrderTO> selectByMemberNumber(Integer memNo);

    void updateAnOrder(ItemOrderTO itemOrderVO);
}
