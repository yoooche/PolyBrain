package com.polybrain.member.service;

import com.polybrain.member.entity.MemVo;

public interface LoginService {
    boolean validateInfo(String memEmail, String memPwd);
    MemVo getMemByValidatedInfo(String memEmail, String memPwd);
    MemVo getMemByMemNo(Integer memNo);
}
