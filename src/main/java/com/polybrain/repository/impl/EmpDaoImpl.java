package com.polybrain.repository.impl;

import com.polybrain.util.HibernateUtil;
import com.polybrain.model.EmpTO;
import org.hibernate.Session;

import java.util.List;
import com.polybrain.repository.EmpDao;
public class EmpDaoImpl implements EmpDao {
    Session session = HibernateUtil.getSessionFactory().openSession();

    @Override
    public Integer insert(EmpTO empVo) {
        session.save(empVo);
        return 1;
    }

    @Override
    public Integer deleteById(Integer id) {
        return null;
    }

    public Integer update(EmpTO empVo) {
        return null;
    }

    @Override
    public Integer updateById(EmpTO empVo) {
        return null;
    }

    @Override
    public EmpTO selectById(Integer empNo) {
        return session.get(EmpTO.class, empNo);
    }

    @Override
    public List<EmpTO> selectAll() {
        final String hql = "FROM employee ORDER BY empNo";
        return session.createQuery(hql, EmpTO.class).getResultList();
    }

    @Override
    public EmpTO selectForLogin(String empEmail, String empPwd) {
        final String sql = "select * from EMPLOYEE where EMP_EMAIL = :empEmail and EMP_PWD = :empPwd";
        return session
                .createNativeQuery(sql, EmpTO.class)
                .setParameter("empEmail", empEmail)
                .setParameter("empPwd", empPwd)
                .uniqueResult();
    }


    @Override
    public List<EmpTO> GETEmailPwd() {
        final String sql = "SELECT EMP_EMAIL,EMP_PWD FROM member";;
        return session.createQuery(sql, EmpTO.class).getResultList();
    }

    @Override
    public int getEmpNoByEmail(String email) {
        String sql = "SELECT EMP_NO FROM employee WHERE EMP_EMAIL = :email";
        Integer empNo = (Integer) session.createNativeQuery(sql)
                .setParameter("email", email)
                .uniqueResult();
        return empNo != null ? empNo : -1; // 返回-1表示未找到员工
    }
}
