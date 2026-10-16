package com.polybrain.repository;

import org.hibernate.sql.Update;
import com.polybrain.model.ArtTO;

import java.util.*;

public interface ArtDaoImpl {
    public void insert(ArtTO artvo);
    public void update(ArtTO artvo);
    public void delete(Integer artNo);
    public  ArtTO findByPrimaryKey(Integer artNo);
    public List<ArtTO> getAll();
    public List<ArtTO> findByItemNo(Integer itemNo);

}