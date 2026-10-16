package com.polybrain.item.service.impl;


import com.polybrain.item.dao.ItemClassDao;
import com.polybrain.item.dao.impl.ItemClassDaoImpl;
import com.polybrain.item.entity.ItemClass;

import java.util.List;
import com.polybrain.item.service.ItemClassService;

public class ItemClassServiceImpl implements ItemClassService{

    private ItemClassDao Dao;

    public  ItemClassServiceImpl(){
            Dao = new ItemClassDaoImpl();
        }

    public ItemClass AddClass(ItemClass item){
        System.out.println("新增遊戲類別");
        final int resultCount = Dao.insert(item);
        if(resultCount < 1){
            item.setMessage("新增失敗，請聯絡管理員!");
            item.setSuccess(false);
        }
        item.setMessage("新增成功");
        item.setSuccess(true);
        return item;
    }

    public ItemClass editClass(ItemClass item){
        System.out.println("修改遊戲類別");
        final int resultCount = Dao.update(item);
        item.setSuccess(resultCount > 0);
        item.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
        return item;
    }

    //刪除類別
    public boolean remove(Integer itemNo) {
        // 交易機制由Filter掌管，這邊不執行交易
        System.out.println("啟動刪除");
        return Dao.deleteById(itemNo) > 0;
    }


    //拿取全部遊戲類別
    @Override
    public List<ItemClass> getAllClasses() {
        System.out.println("查詢所有遊戲類別");
        return Dao.selectAll();
    }
}
