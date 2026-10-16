package com.polybrain.repository;

import java.util.List;
import java.util.Map;

import com.polybrain.model.AdminTablebookingTO;

public interface Tablebooking_interface extends com.polybrain.repository.CoreDao<AdminTablebookingTO, Integer>{
	
	public void insert(AdminTablebookingTO tablebookingVO);
    public void update(AdminTablebookingTO tablebookingVO);
    public AdminTablebookingTO findByPrimaryKey(Integer TABLE_BOOK_NO);
    public List<AdminTablebookingTO> getAll();

    public List<AdminTablebookingTO> query(java.sql.Date TABLE_DATE, Integer TABLE_NO);
    //查詢某部門的員工(一對多)(回傳 Set)
    public List<AdminTablebookingTO> getten();

}

