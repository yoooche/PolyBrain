package com.polybrain.repository;

import com.polybrain.model.BidItemPicTO;

import java.util.List;

public interface BidItemPicDao {
    public void insert(BidItemPicTO bidItemPicVo);
    public List<byte[]> selectPicsById(Integer bidItemNo);
    public List<BidItemPicTO> selectAllPics();
    public Integer deleteByBidItemNo(Integer BidItemNo);
    public Integer update(BidItemPicTO bidItemPicVo);
}
