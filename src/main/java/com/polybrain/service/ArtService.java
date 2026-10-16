package com.polybrain.service;

import com.polybrain.repository.impl.ArtDao;
import com.polybrain.repository.ArtDaoImpl;
import com.polybrain.model.ArtTO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ArtService {
    private ArtDaoImpl dao;

    public ArtService(){
        dao = new ArtDao();
    }

    public ArtTO addArt(Integer memNo, String artTitle, String artCon, Integer itemNo,byte[] upFiles){

        ArtTO artVo =new ArtTO();

        artVo.setMemNo(memNo);
        artVo.setArtTitle(artTitle);
        artVo.setArtCon(artCon);
        artVo.setItemNo(itemNo);
        artVo.setUpFiles(upFiles);
        dao.insert(artVo);

        return artVo;
    }
    public ArtTO updateArt(Integer artNo, Integer memNo, String artTitle, String artCon, Timestamp artTime, Byte artState, Integer itemNo, byte[] upFiles){

        ArtTO artVo =new ArtTO();

        artVo.setArtNo(artNo);
        artVo.setMemNo(memNo);
        artVo.setArtTitle(artTitle);
        artVo.setArtCon(artCon);
        artVo.setArtTime(artTime);
        artVo.setArtState(artState);
        artVo.setItemNo(itemNo);
        artVo.setUpFiles(upFiles);
        dao.update(artVo);

        return dao.findByPrimaryKey(artNo);
    }
    public void updateArt(ArtTO artVo) {
        dao.update(artVo);
    }

    public void deleteArt(Integer artNo) {
        dao.delete(artNo);
    }

    public ArtTO getOneArt(Integer artNo) {
        return dao.findByPrimaryKey(artNo);
    }

    public List<ArtTO> getAll() {

        return dao.getAll();
    }

    public List<ArtTO> findByItemNo(Integer itemNo) {
        return dao.findByItemNo(itemNo);
    }





}
