package com.polybrain.service;

import com.polybrain.repository.MemDao;
import com.polybrain.repository.impl.MemDaoImpl;
import com.polybrain.model.MemTO;

import java.sql.Date;
import java.util.List;

public class listallmemService {
    private MemDao memDao;

    public listallmemService() {
        memDao = new MemDaoImpl();
    }

    public List<MemTO> getAll() {
        return memDao.getAll();
    }

    public MemTO getOneMem(Integer memNo) {
        return memDao.findByPrimaryKey(memNo);
    }

    public void deleteMem(Integer memNo) {
        memDao.delete(memNo);
    }

    public MemTO updateMem(Integer memNo, String memName, String memPid, String memEmail, String memPh, String memAddress, Date memBirth, Byte memAuth) {

        MemTO memVo = new MemTO();

        memVo.setMemNo(memNo);
        memVo.setMemName(memName);
        memVo.setMemPid(memPid);
        memVo.setMemEmail(memEmail);
        memVo.setMemPh(memPh);
        memVo.setMemAddress(memAddress);
        memVo.setMemBirth(memBirth);
        memVo.setMemAuth(memAuth);
        memDao.updateJDBC(memVo);

        return memDao.findByPrimaryKey(memNo);
    }




}
