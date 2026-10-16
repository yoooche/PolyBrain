package com.polybrain.repository;

import com.polybrain.model.BidOrderTO;

import java.util.List;

public interface BidOrderDao {
    public BidOrderTO insert(BidOrderTO bidOrderVo);
    public BidOrderTO selectById(Integer bidOrderNo);

}
