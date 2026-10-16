package com.polybrain.employee.service;

import com.polybrain.employee.dao.impl.EmpListDao;
import com.polybrain.employee.dao.EmpListDaoImpl;
import com.polybrain.employee.entity.EmpVo;

import javax.servlet.ServletException;

public class empAddService {
    private EmpListDaoImpl dao;

    public empAddService() {dao = new EmpListDao();}


    public String Add(String name, Byte gender, String email, String password, String phone, Byte state) throws ServletException {

        // 创建一个新的 MemberVO 对象
        EmpVo emp = new EmpVo();
        emp.setEmpName(name);
        emp.setEmpGender(gender);
        emp.setEmpEmail(email);
        emp.setEmpPwd(password);
        emp.setEmpPh(phone);
        emp.setEmpState(state);

        // 调用DAO的createMember方法将会员数据存入数据库
        try {
            dao.createEmp(emp);
            return "success"; // 注册成功
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("注册失败，请稍后重试", e); // 注册失败
        }
    }
}