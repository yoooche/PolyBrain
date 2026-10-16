package com.polybrain.forum.dao;

import org.hibernate.sql.Update;
import com.polybrain.forum.entity.ArtVo;

import java.util.*;

public interface ArtDaoImpl {
    public void insert(ArtVo artvo);
    public void update(ArtVo artvo);
    public void delete(Integer artNo);
    public  ArtVo findByPrimaryKey(Integer artNo);
    public List<ArtVo> getAll();
    public List<ArtVo> findByItemNo(Integer itemNo);

}