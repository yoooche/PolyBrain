package com.polybrain.forum.dao;

import com.polybrain.forum.entity.ArtVo;
import com.polybrain.forum.entity.RpyVo;

import java.util.List;

public interface RpyDaoImpl {
    public void insert(RpyVo rpyvo);
    public void update(RpyVo rpyvo);
    public void delete(Integer rpyNo);
    public  RpyVo findByPrimaryKey(Integer rpyNo);
    public List<RpyVo> getAll();
    public List<RpyVo> findByArt(Integer artNo);

}
