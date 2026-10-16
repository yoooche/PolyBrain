package com.polybrain.member.service;

import com.polybrain.member.dao.MemDao;
import com.polybrain.member.dao.impl.MemDaoImpl;
import com.polybrain.member.entity.MemVo;

import java.sql.Date;

public class MemInformationService {
    private MemDao memDao;
    public MemInformationService() {
        memDao = new MemDaoImpl();
    }

    public MemVo updateInformation(Integer memNo, String memName ,String memEmail ,String memPh,String memPwd,String memPid,String memAddress,Date memBirth ,Byte memGender) {

        MemVo memVo = new MemVo();

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
