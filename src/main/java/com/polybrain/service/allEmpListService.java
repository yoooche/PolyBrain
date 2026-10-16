package com.polybrain.service;
import com.polybrain.repository.impl.EmpListDao;
import com.polybrain.repository.EmpListDaoImpl;
import com.polybrain.model.EmpTO;

import java.util.List;

public class allEmpListService {

    private EmpListDaoImpl dao;

    public allEmpListService() {
        dao = new EmpListDao();
    }

    public List<EmpTO> getAll() {
        return dao.getAll();
    }
    public EmpTO getOneEmp(Integer empNo) {
        return dao.findByPrimaryKey(empNo);
    }

    public void deleteEmp(Integer empNo) {
        dao.delete(empNo);
    }

    public EmpTO updateEmp(Integer empNo, String empName, Byte empGender, String empEmail, String empPwd, String empPh, Byte empState) {

        EmpTO empVo = new EmpTO();

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
