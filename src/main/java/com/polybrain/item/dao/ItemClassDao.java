package com.polybrain.item.dao;


import com.polybrain.common.dao.CoreDao;
import com.polybrain.item.entity.ItemClass;

import java.util.List;

public interface ItemClassDao extends CoreDao<ItemClass, Integer> {
    //搜尋所有商品類別
    List<ItemClass> selectAll();
    //增加商品類別
    Integer insert(ItemClass item);
    //刪除商品
    Integer deleteById(Integer itemClassNo);
    //修改商品類別
   Integer update(ItemClass itemclass);

    ItemClass selectByItemClassNo(Integer itemClassNo);
}
