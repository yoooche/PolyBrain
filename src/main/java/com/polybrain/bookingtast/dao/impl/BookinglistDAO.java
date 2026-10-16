package com.polybrain.bookingtast.dao.impl;

import com.polybrain.common.util.HibernateUtil;
import com.polybrain.bookingtast.entity.BookinglistVO;
import com.polybrain.bookingtast.entity.TablebookingVO;
import org.hibernate.Session;

import java.util.List;
import com.polybrain.bookingtast.dao.Bookinglist_interface;

public class BookinglistDAO implements Bookinglist_interface {

	Session session = HibernateUtil.getSessionFactory().openSession();

	public void insert(BookinglistVO bookinglistVO) {
		session.save(bookinglistVO);
	}

	public void update(BookinglistVO bookinglistVO) {
		session.update(bookinglistVO);
	}

	public BookinglistVO findByPrimaryKey(Integer BOOKING_NO) {
		return session.get(BookinglistVO.class, BOOKING_NO);
	}

	public List<BookinglistVO> getAll() {
		final String hql = "FROM bookinglist ORDER BY BOOKING_NO";
		return session.createQuery(hql, BookinglistVO.class).getResultList();
	}

	@Override
	public List<TablebookingVO> selectAll() {
		return null;
	}


	// ... 其他方法可以類似地使用Hibernate進行轉換

}
