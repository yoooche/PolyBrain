package com.polybrain.repository;

import com.polybrain.model.EmpTO;

import java.util.List;

public interface EmpListDaoImpl {

    public List<EmpTO> getAll();

    public EmpTO findByPrimaryKey(Integer empNo);

    public void delete(Integer empNo);

    public void update(EmpTO empNo);

    void createEmp(EmpTO emp);
}
