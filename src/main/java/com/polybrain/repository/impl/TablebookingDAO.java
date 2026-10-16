package com.polybrain.repository.impl;

import com.polybrain.util.HibernateUtil;
import com.polybrain.model.AdminTablebookingTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.polybrain.repository.Tablebooking_interface;

public class TablebookingDAO implements Tablebooking_interface {

	Session session = HibernateUtil.getSessionFactory().openSession();

	@Override
	public void insert(AdminTablebookingTO tablebookingVO) {
		Session session = getSession();
		session.save(tablebookingVO);
	}

	@Override
	public void update(AdminTablebookingTO tablebookingVO) {
		Transaction tx = session.beginTransaction();
		try {
			session.update(tablebookingVO);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e; // 或者你可以選擇處理異常
		}
//		finally {
//			session.close();
//		}
	}

	@Override
	public AdminTablebookingTO findByPrimaryKey(Integer TABLE_BOOK_NO) {
		return session.get(AdminTablebookingTO.class, TABLE_BOOK_NO);
	}

	@Override
	public List<AdminTablebookingTO> getAll() {
		final String hql = "FROM AdminTablebookingTO ORDER BY TABLE_BOOK_NO";
		return getSession().createQuery(hql, AdminTablebookingTO.class).getResultList();
	}


	@Override
	public List<AdminTablebookingTO> getten() {
		final String hql = "FROM AdminTablebookingTO WHERE TABLE_DATE BETWEEN CURRENT_DATE AND DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY) ORDER BY TABLE_DATE";
		return session.createQuery(hql, AdminTablebookingTO.class).getResultList();
	}

	public List<AdminTablebookingTO> query(java.sql.Date TABLE_DATE, Integer TABLE_NO) {
		final String hql = "FROM AdminTablebookingTO WHERE TABLE_DATE = :TABLE_DATE AND TABLE_NO = :TABLE_NO AND PERIOD_TIME NOT IN (1,2)";
		return session.createQuery(hql, AdminTablebookingTO.class).getResultList();
	}

	public void dynamicUpdate(Integer primaryKey, Map<String, Object> updatedFields) {
		StringBuilder hql = new StringBuilder("UPDATE AdminTablebookingTO SET ");
		List<Object> params = new ArrayList<>();
		for (Map.Entry<String, Object> entry : updatedFields.entrySet()) {
			hql.append(entry.getKey()).append(" = ?, ");
			params.add(entry.getValue());
		}
		hql.delete(hql.length() - 2, hql.length());
		hql.append(" WHERE TABLE_BOOK_NO = ? ");
		params.add(primaryKey);
		Query<?> query = session.createQuery(hql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		Transaction tx = session.beginTransaction();
		try {
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		}
	}

	@Override
	public List<AdminTablebookingTO> selectAll() {
		return null;
	}
}