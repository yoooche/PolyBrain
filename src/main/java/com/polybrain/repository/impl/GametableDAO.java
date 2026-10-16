package com.polybrain.repository.impl;

import com.polybrain.util.HibernateUtil;
import com.polybrain.model.AdminGametableTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import com.polybrain.repository.Gametable_interface;

public class GametableDAO implements Gametable_interface {

	Session session = HibernateUtil.getSessionFactory().openSession();

	@Override
	public void insert(AdminGametableTO gametableVO) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(gametableVO);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			throw e;
		}
	}

	@Override
	public void update(AdminGametableTO gametableVO) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(gametableVO);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			throw e;
		}
	}

	@Override
	public void delete(Integer TABLE_NO) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AdminGametableTO gametableVO = session.get(AdminGametableTO.class, TABLE_NO);
			if (gametableVO != null) {
				session.delete(gametableVO);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			throw e;
		}
	}

	@Override
	public AdminGametableTO findByPrimaryKey(Integer TABLE_NO) {
		return session.get(AdminGametableTO.class, TABLE_NO);
	}

	@Override
	public List<AdminGametableTO> getAll() {
		String hql = "FROM AdminGametableTO";
		return session.createQuery(hql, AdminGametableTO.class).getResultList();
	}


	@Override
	public List<AdminGametableTO> selectAll() {
		return null;
	}
}
