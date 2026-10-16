package com.polybrain.service;

import com.polybrain.service.CoreService;
import com.polybrain.model.Item;
import com.polybrain.model.ItemImg;

import java.util.List;

public interface ItemImgService extends CoreService {
    //新增商品資訊
    void AddItemImg(List<String> itemImageList, Integer itemNo);
//    //移除商品資訊
    boolean removeImg(Integer itemNo);

//    //編輯商品資訊
    void editImg(List<String> itemImageList, Integer itemNo);

}
