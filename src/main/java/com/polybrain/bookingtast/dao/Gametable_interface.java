package com.polybrain.bookingtast.dao;

import com.polybrain.common.dao.CoreDao;
import com.polybrain.bookingtast.entity.GametableVO;

import java.util.*;



public interface Gametable_interface extends CoreDao<GametableVO, Integer> {
	public void insert(GametableVO gametableVO);
    public void update(GametableVO gametableVO);
    public void delete(Integer TABLE_NO);
    public GametableVO findByPrimaryKey(Integer TABLE_NO);
    public List<GametableVO> getAll();

}
