package com.polybrain.order.dao;

import com.polybrain.common.dao.CoreDao;
import com.polybrain.order.entity.ItemOrderDetailVO;
import com.polybrain.order.entity.ItemOrderVO;

import java.util.List;

public interface ItemOrderDAO extends CoreDao<ItemOrderVO, Integer>{


    List<ItemOrderDetailVO> getDetailByOrderNumber(Integer orderNo);

    List<ItemOrderVO> selectByMemberNumber(Integer memNo);

    ItemOrderVO updateAnOrder(ItemOrderVO itemordervo);

}
