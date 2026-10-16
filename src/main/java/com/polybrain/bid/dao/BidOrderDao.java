package com.polybrain.bid.dao;

import com.polybrain.bid.entity.BidOrderVo;

import java.util.List;

public interface BidOrderDao {
    public BidOrderVo insert(BidOrderVo bidOrderVo);
    public BidOrderVo selectById(Integer bidOrderNo);

}
