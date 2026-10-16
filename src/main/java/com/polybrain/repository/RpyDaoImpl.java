package com.polybrain.repository;

import com.polybrain.model.ArtTO;
import com.polybrain.model.RpyTO;

import java.util.List;

public interface RpyDaoImpl {
    public void insert(RpyTO rpyvo);
    public void update(RpyTO rpyvo);
    public void delete(Integer rpyNo);
    public  RpyTO findByPrimaryKey(Integer rpyNo);
    public List<RpyTO> getAll();
    public List<RpyTO> findByArt(Integer artNo);

}
