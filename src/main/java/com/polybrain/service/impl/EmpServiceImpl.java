package com.polybrain.service.impl;

import com.polybrain.repository.EmpDao;
import com.polybrain.repository.impl.EmpDaoImpl;
import com.polybrain.service.EmpService;
import com.polybrain.model.EmpTO;

import java.util.List;
public class EmpServiceImpl implements EmpService {
    private EmpDao empDao;
    public EmpServiceImpl(){
        empDao = new EmpDaoImpl();
    }

    public List<EmpTO> getAll(){
        return empDao.selectAll();
    }
    public EmpTO getOneEmp(Integer empNo){
        return empDao.selectById(empNo);
    }

    public EmpTO empLogin(EmpTO empVo){
        final String email = empVo.getEmpEmail();
        final String password = empVo.getEmpPwd();

        if (email == null || email.trim().isEmpty()) {
            empVo.setMessage("管理員帳號未輸入");
            empVo.setSuccess(false);
            return empVo;
        }
        if (password == null || password.trim().isEmpty()) {
            empVo.setMessage("管理員密碼未輸入");
            empVo.setSuccess(false);
            return empVo;
        }
        empDao = new EmpDaoImpl();
        empVo = empDao.selectForLogin(email, password);

        if (empVo == null) {
            empVo = new EmpTO();
            empVo.setMessage("管理員帳號或密碼錯誤");
            empVo.setSuccess(false);
            return empVo;
        }
        empVo.setMessage("登入成功");
        empVo.setSuccess(true);
        return empVo;
    }
}
