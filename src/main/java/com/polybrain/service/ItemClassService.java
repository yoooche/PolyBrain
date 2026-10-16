package com.polybrain.service;

import com.polybrain.service.CoreService;
import com.polybrain.model.ItemClass;

import java.util.List;


public interface ItemClassService extends CoreService {
    //取得全部商品類別資訊
    List<ItemClass> getAllClasses();
    //增加類別
    ItemClass AddClass(ItemClass item);
    //修改類別
    ItemClass editClass(ItemClass item);
    //刪除類別
    boolean remove(Integer itemNo);
}
