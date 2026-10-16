package com.polybrain.service;

import java.util.List;

import com.polybrain.repository.impl.BookinglistDAO;
import com.polybrain.repository.Bookinglist_interface;
import com.polybrain.model.BookinglistTO;

public class BookinglistService {

	private Bookinglist_interface dao;

	public BookinglistService() {
		dao = new BookinglistDAO();
	}


	
	public BookinglistTO addBookinglist(Integer TABLE_NO, java.sql.Date TABLE_DATE, Integer PERIOD_TIME) {

		BookinglistTO bookinglistVO = new BookinglistTO();
	
		bookinglistVO.setTABLE_NO(TABLE_NO);
		bookinglistVO.setTABLE_DATE(TABLE_DATE);
		bookinglistVO.setPERIOD_TIME(PERIOD_TIME);
		
		dao.insert(bookinglistVO);

		return bookinglistVO;
	}
	
	//預留給 Struts 2 或 Spring MVC 用
		public void addBookinglist(BookinglistTO bookinglistVO) {
			dao.insert(bookinglistVO);
		}
		
		public BookinglistTO updatebookinglist(Integer BOOKING_NO,Integer TABLE_NO, java.sql.Date TABLE_DATE, Integer PERIOD_TIME) {
			
			BookinglistTO bookinglistVO = new BookinglistTO();
			bookinglistVO.setBOOKING_NO(BOOKING_NO);
			bookinglistVO.setTABLE_NO(TABLE_NO);
			bookinglistVO.setTABLE_DATE(TABLE_DATE);
			bookinglistVO.setPERIOD_TIME(PERIOD_TIME);
			dao.update(bookinglistVO);

			return dao.findByPrimaryKey(BOOKING_NO);
		}
		
		//預留給 Struts 2 或 Spring MVC 用
		public void updateBookinglis(BookinglistTO bookinglistVO) {
			dao.update(bookinglistVO);
		}
		


		public BookinglistTO getOneBookinglis(Integer BOOKING_NO) {
			return dao.findByPrimaryKey(BOOKING_NO);
		}
		
		public List<BookinglistTO> getAll() {
			return dao.getAll();
		}
		
}
