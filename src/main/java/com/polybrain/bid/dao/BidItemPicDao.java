package com.polybrain.bid.dao;

import com.polybrain.bid.entity.BidItemPicVo;

import java.util.List;

public interface BidItemPicDao {
    public void insert(BidItemPicVo bidItemPicVo);
    public List<byte[]> selectPicsById(Integer bidItemNo);
    public List<BidItemPicVo> selectAllPics();
    public Integer deleteByBidItemNo(Integer BidItemNo);
    public Integer update(BidItemPicVo bidItemPicVo);
}
