package com.polybrain.bid.dao;

import com.polybrain.bid.entity.BidEventVo;

import java.util.List;

public interface BidEventDao {
    public void insert(BidEventVo bidEventVo);
    public List<BidEventVo> selectAll();
    public void deleteById(Integer bidEventNo);
    public BidEventVo selectById(Integer bidEventNo);
    public String selectItemNameByEveNo(Integer bidEventNo);
    public Integer selectItemNoByEveNo(Integer bidEventNo);
}
