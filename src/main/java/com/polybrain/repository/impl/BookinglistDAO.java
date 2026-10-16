package com.polybrain.repository.impl;

import com.polybrain.util.HibernateUtil;
import com.polybrain.model.BookinglistTO;
import com.polybrain.model.AdminTablebookingTO;
import org.hibernate.Session;

import java.util.List;
import com.polybrain.repository.Bookinglist_interface;

public class BookinglistDAO implements Bookinglist_interface {

	Session session = HibernateUtil.getSessionFactory().openSession();

	public void insert(BookinglistTO bookinglistVO) {
		session.save(bookinglistVO);
	}

	public void update(BookinglistTO bookinglistVO) {
		session.update(bookinglistVO);
	}

	public BookinglistTO findByPrimaryKey(Integer BOOKING_NO) {
		return session.get(BookinglistTO.class, BOOKING_NO);
	}

	public List<BookinglistTO> getAll() {
		final String hql = "FROM bookinglist ORDER BY BOOKING_NO";
		return session.createQuery(hql, BookinglistTO.class).getResultList();
	}

	@Override
	public List<AdminTablebookingTO> selectAll() {
		return null;
	}


	// ... 其他方法可以類似地使用Hibernate進行轉換

}
