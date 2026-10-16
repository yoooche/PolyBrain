package com.polybrain.service;

import com.polybrain.repository.MemDao;
import com.polybrain.repository.impl.MemDaoImpl;
import com.polybrain.model.MemTO;

import java.sql.Date;

public class MemInformationService {
    private MemDao memDao;
    public MemInformationService() {
        memDao = new MemDaoImpl();
    }

    public MemTO updateInformation(Integer memNo, String memName ,String memEmail ,String memPh,String memPwd,String memPid,String memAddress,Date memBirth ,Byte memGender) {

        MemTO memVo = new MemTO();

        memVo.setMemNo(memNo);
        memVo.setMemName(memName);
        memVo.setMemEmail(memEmail);
        memVo.setMemPh(memPh);
        memVo.setMemPwd(memPwd);
        memVo.setMemPid(memPid);
        memVo.setMemAddress(memAddress);
        memVo.setMemBirth(memBirth);
        memVo.setMemGender(memGender);
        memDao.updateInformation(memVo);

        return memDao.getMemberByMemNo(memNo);
    }
}
