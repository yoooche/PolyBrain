package com.polybrain.service;


import com.polybrain.repository.impl.RpyDao;
import com.polybrain.repository.RpyDaoImpl;
import com.polybrain.model.ArtTO;
import com.polybrain.model.RpyTO;

import java.util.Date;
import java.util.List;

public class RpyService {
    private RpyDaoImpl dao;

    public RpyService(){
        dao = new RpyDao();
    }

    public RpyTO addRpy(Integer memNo, Integer artNo, String rpyCon){

        RpyTO rpyVo =new RpyTO();

        rpyVo.setMemNo(memNo);
        rpyVo.setArtNo(artNo);
        rpyVo.setRpyCon(rpyCon);

        dao.insert(rpyVo);

        return rpyVo;
    }
    public RpyTO updateRpy(Integer rpyNo,Integer memNo, Integer artNo, String rpyCon){

        RpyTO rpyVo =new RpyTO();

        rpyVo.setRpyNo(rpyNo);
        rpyVo.setMemNo(memNo);
        rpyVo.setArtNo(artNo);
        rpyVo.setRpyCon(rpyCon);

        dao.update(rpyVo);

        return dao.findByPrimaryKey(rpyNo);
    }
    public void updateRpy(RpyTO rpyVo) {
        dao.update(rpyVo);
    }

    public void deleteRpy(Integer rpyNo) {

        dao.delete(rpyNo);
    }

    public RpyTO getOneRpy(Integer rpyNo) {
        return dao.findByPrimaryKey(rpyNo);
    }

    public List<RpyTO> getAll() {

        return dao.getAll();
    }

    public List<RpyTO> findByArt(Integer artNo) {
        return dao.findByArt(artNo);
    }
}
