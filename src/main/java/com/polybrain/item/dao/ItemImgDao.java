package com.polybrain.item.dao;


import com.polybrain.common.dao.CoreDao;
import com.polybrain.item.entity.Item;
import com.polybrain.item.entity.ItemImg;

import java.util.List;

public interface ItemImgDao extends CoreDao<ItemImg, Integer> {
    //新增圖片
    Integer insert(ItemImg itemImg);
//    json.stringify(item)
//    Item item json2pojo(req, Item.class)
//        List<ItemImg> itemimg1 = item.getItemImg();
//    byte[] itemimg = item.getItemImg()[0].getItemImg();
    //刪除圖片
    Integer deleteById(Integer itemImgNo);
    //刪除單一商品的所有圖片
    Integer deleteByItem(Integer itemNo);

    //更新圖片
    Integer update(ItemImg itemImg);
    List<ItemImg> selectAll();
}
