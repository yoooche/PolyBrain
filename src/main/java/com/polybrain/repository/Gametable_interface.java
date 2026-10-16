package com.polybrain.repository;

import com.polybrain.repository.CoreDao;
import com.polybrain.model.AdminGametableTO;

import java.util.*;



public interface Gametable_interface extends CoreDao<AdminGametableTO, Integer> {
	public void insert(AdminGametableTO gametableVO);
    public void update(AdminGametableTO gametableVO);
    public void delete(Integer TABLE_NO);
    public AdminGametableTO findByPrimaryKey(Integer TABLE_NO);
    public List<AdminGametableTO> getAll();

}
