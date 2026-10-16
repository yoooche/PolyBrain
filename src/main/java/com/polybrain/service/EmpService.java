package com.polybrain.service;

import com.polybrain.service.CoreService;
import com.polybrain.model.EmpTO;
import org.springframework.stereotype.Service;

import java.util.List;
public interface EmpService extends CoreService {
    List<EmpTO> getAll();
    EmpTO getOneEmp(Integer empNo);
    EmpTO empLogin(EmpTO empVo);
}
