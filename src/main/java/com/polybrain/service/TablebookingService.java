package com.polybrain.service;

import java.util.List;

import com.polybrain.repository.impl.TablebookingDAO;
import com.polybrain.repository.Tablebooking_interface;
import com.polybrain.model.AdminTablebookingTO;

import javax.servlet.http.HttpServletRequest;

public class TablebookingService  {
	private Tablebooking_interface dao;

	public TablebookingService() {
		dao = new TablebookingDAO();
	}

	public static Integer parseIntegerParameter(HttpServletRequest req, String paramName) {
		String paramValue = req.getParameter(paramName);
		return (paramValue != null) ? Integer.valueOf(paramValue.trim()) : null;
	}

	public AdminTablebookingTO addtablebooking(java.sql.Date TABLE_DATE, Integer TABLE_NO, Integer TABLE_MOR,
			Integer TABLE_EVE, Integer TABLE_NIGHT) {

		AdminTablebookingTO tablebookingVO = new AdminTablebookingTO();

		tablebookingVO.setTABLE_DATE(TABLE_DATE);
		tablebookingVO.setTABLE_NO(TABLE_NO);
		tablebookingVO.setTABLE_MOR(TABLE_MOR);
		tablebookingVO.setTABLE_EVE(TABLE_EVE);
		tablebookingVO.setTABLE_NIGHT(TABLE_NIGHT);
		
		dao.insert(tablebookingVO);

		return tablebookingVO;
	}
	public AdminTablebookingTO updatetab( Integer TABLE_BOOK_NO,java.sql.Date TABLE_DATE, Integer TABLE_NO,Integer TABLE_MOR, Integer TABLE_EVE, Integer TABLE_NIGHT) {

		AdminTablebookingTO tablebookingVO = new AdminTablebookingTO();

		tablebookingVO.setTABLE_BOOK_NO(TABLE_BOOK_NO);
		tablebookingVO.setTABLE_DATE(TABLE_DATE);
		tablebookingVO.setTABLE_NO(TABLE_NO);
		tablebookingVO.setTABLE_MOR(TABLE_MOR);
		tablebookingVO.setTABLE_EVE(TABLE_EVE);
		tablebookingVO.setTABLE_NIGHT(TABLE_NIGHT);

		dao.update(tablebookingVO);

		return dao.findByPrimaryKey(TABLE_BOOK_NO);
	}
	public void updatetab(AdminTablebookingTO tablebookingVO) {
		dao.update(tablebookingVO);
	}
	public AdminTablebookingTO getOneTablebooking(Integer TABLE_BOOK_NO) {
		return dao.findByPrimaryKey(TABLE_BOOK_NO);
	}






	public List<AdminTablebookingTO> getAll() {
		return dao.getAll();
	}

	public List<AdminTablebookingTO> getten() {
		return dao.getten();
	}



//	public Set<AdminTablebookingTO> getEmpsByTABLE_DATE(Date TABLE_DATE) {
//		return dao.getOneTablebooking(TABLE_DATE);
//	}


	
	
	
}
