package com.polybrain.service.impl;

import com.polybrain.repository.MemDao;
import com.polybrain.repository.impl.MemDaoImpl;
import com.polybrain.model.MemTO;

import java.util.List;
import com.polybrain.service.LoginService;

public class LoginServiceImpl implements LoginService{
    private MemDao memDao;

    public LoginServiceImpl(){
        memDao = new MemDaoImpl();
    }

    public boolean isValidUser(String email, String password) {
        // 查詢所有會員
        List<MemTO> members = memDao.getAllMembers();
        // 檢查是否有匹配的電子郵件和密碼
        for (MemTO member : members) {
            if (email.equals(member.getMemEmail()) && password.equals(member.getMemPwd())) {
                return true; // 有效的用戶
            }
        }
        return false; // 無效的用戶
    }

    @Override
    public boolean validateInfo(String memEmail, String memPwd) {
        if(memDao.selectByMemEmailAndPwd(memEmail, memPwd) != null){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public MemTO getMemByValidatedInfo(String memEmail, String memPwd) {
        return memDao.selectByMemEmailAndPwd(memEmail, memPwd);

    }

    @Override
    public MemTO getMemByMemNo(Integer memNo) {
        return memDao.selectById(memNo);
    }
}
