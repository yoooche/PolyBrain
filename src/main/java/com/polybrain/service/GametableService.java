package com.polybrain.service;
import java.util.List;

import com.polybrain.repository.impl.GametableDAO;
import com.polybrain.repository.Gametable_interface;
import com.polybrain.model.AdminGametableTO;

public class GametableService {

	private Gametable_interface dao;
	
	public GametableService() {
		dao = new GametableDAO();
	}
	
	public List<AdminGametableTO> getAll() {
		return dao.getAll();
	}
	
	public AdminGametableTO getOneGametable(Integer TABLE_NO) {
		return dao.findByPrimaryKey(TABLE_NO);
	}

//	public Set<AdminGametableTO> getEmpsByTABLE_NO(Integer TABLE_NO) {
//		return dao.getEmpsByTABLE_NO(TABLE_NO);
//	}

}
