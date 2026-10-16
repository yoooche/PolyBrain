package com.polybrain.employee.service;

import com.polybrain.common.service.CoreService;
import com.polybrain.employee.entity.EmpVo;
import org.springframework.stereotype.Service;

import java.util.List;
public interface EmpService extends CoreService {
    List<EmpVo> getAll();
    EmpVo getOneEmp(Integer empNo);
    EmpVo empLogin(EmpVo empVo);
}
