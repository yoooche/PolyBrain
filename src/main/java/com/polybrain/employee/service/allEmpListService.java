package com.polybrain.employee.service;
import com.polybrain.employee.dao.impl.EmpListDao;
import com.polybrain.employee.dao.EmpListDaoImpl;
import com.polybrain.employee.entity.EmpVo;

import java.util.List;

public class allEmpListService {

    private EmpListDaoImpl dao;

    public allEmpListService() {
        dao = new EmpListDao();
    }

    public List<EmpVo> getAll() {
        return dao.getAll();
    }
    public EmpVo getOneEmp(Integer empNo) {
        return dao.findByPrimaryKey(empNo);
    }

    public void deleteEmp(Integer empNo) {
        dao.delete(empNo);
    }

    public EmpVo updateEmp(Integer empNo, String empName, Byte empGender, String empEmail, String empPwd, String empPh, Byte empState) {

        EmpVo empVo = new EmpVo();

        empVo.setEmpNo(empNo);
        empVo.setEmpName(empName);
        empVo.setEmpGender(empGender);
        empVo.setEmpEmail(empEmail);
        empVo.setEmpPwd(empPwd);
        empVo.setEmpPh(empPh);
        empVo.setEmpState(empState);
        dao.update(empVo);

        return dao.findByPrimaryKey(empNo);
    }

}
