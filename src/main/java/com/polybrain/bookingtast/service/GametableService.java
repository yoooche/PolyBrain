package com.polybrain.bookingtast.service;
import java.util.List;

import com.polybrain.bookingtast.dao.impl.GametableDAO;
import com.polybrain.bookingtast.dao.Gametable_interface;
import com.polybrain.bookingtast.entity.GametableVO;

public class GametableService {

	private Gametable_interface dao;
	
	public GametableService() {
		dao = new GametableDAO();
	}
	
	public List<GametableVO> getAll() {
		return dao.getAll();
	}
	
	public GametableVO getOneGametable(Integer TABLE_NO) {
		return dao.findByPrimaryKey(TABLE_NO);
	}

//	public Set<GametableVO> getEmpsByTABLE_NO(Integer TABLE_NO) {
//		return dao.getEmpsByTABLE_NO(TABLE_NO);
//	}

}
