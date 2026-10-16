package com.polybrain.service;

import com.polybrain.model.MemTO;

public interface LoginService {
    boolean validateInfo(String memEmail, String memPwd);
    MemTO getMemByValidatedInfo(String memEmail, String memPwd);
    MemTO getMemByMemNo(Integer memNo);
}
