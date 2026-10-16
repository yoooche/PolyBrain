package com.polybrain.forum.service;


import com.polybrain.forum.dao.impl.RpyDao;
import com.polybrain.forum.dao.RpyDaoImpl;
import com.polybrain.forum.entity.ArtVo;
import com.polybrain.forum.entity.RpyVo;

import java.util.Date;
import java.util.List;

public class RpyService {
    private RpyDaoImpl dao;

    public RpyService(){
        dao = new RpyDao();
    }

    public RpyVo addRpy(Integer memNo, Integer artNo, String rpyCon){

        RpyVo rpyVo =new RpyVo();

        rpyVo.setMemNo(memNo);
        rpyVo.setArtNo(artNo);
        rpyVo.setRpyCon(rpyCon);

        dao.insert(rpyVo);

        return rpyVo;
    }
    public RpyVo updateRpy(Integer rpyNo,Integer memNo, Integer artNo, String rpyCon){

        RpyVo rpyVo =new RpyVo();

        rpyVo.setRpyNo(rpyNo);
        rpyVo.setMemNo(memNo);
        rpyVo.setArtNo(artNo);
        rpyVo.setRpyCon(rpyCon);

        dao.update(rpyVo);

        return dao.findByPrimaryKey(rpyNo);
    }
    public void updateRpy(RpyVo rpyVo) {
        dao.update(rpyVo);
    }

    public void deleteRpy(Integer rpyNo) {

        dao.delete(rpyNo);
    }

    public RpyVo getOneRpy(Integer rpyNo) {
        return dao.findByPrimaryKey(rpyNo);
    }

    public List<RpyVo> getAll() {

        return dao.getAll();
    }

    public List<RpyVo> findByArt(Integer artNo) {
        return dao.findByArt(artNo);
    }
}
