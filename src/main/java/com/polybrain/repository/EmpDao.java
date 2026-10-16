package com.polybrain.repository;

import com.polybrain.model.EmpTO;

import java.util.List;
public interface EmpDao extends com.polybrain.repository.CoreDao<EmpTO, Integer> {
        Integer insert(EmpTO empVo);
        Integer deleteById(Integer empNo);
        Integer updateById(EmpTO empVo);
        EmpTO selectById(Integer empNo);
        List<EmpTO> selectAll();
        EmpTO selectForLogin(String empEmail, String empPwd);

        int getEmpNoByEmail(String email);




        public List<EmpTO> GETEmailPwd();
}
