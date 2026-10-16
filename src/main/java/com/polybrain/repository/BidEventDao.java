package com.polybrain.repository;

import com.polybrain.model.BidEventTO;

import java.util.List;

public interface BidEventDao {
    public void insert(BidEventTO bidEventVo);
    public List<BidEventTO> selectAll();
    public void deleteById(Integer bidEventNo);
    public BidEventTO selectById(Integer bidEventNo);
    public String selectItemNameByEveNo(Integer bidEventNo);
    public Integer selectItemNoByEveNo(Integer bidEventNo);
}
