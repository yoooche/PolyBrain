package com.polybrain.repository;

import com.polybrain.model.BidItemTO;

import java.util.List;

public interface BidItemDao {
    public Integer insert(BidItemTO bidItemVo);
    public List<BidItemTO> selectAll();
    public Integer deleteById(Integer bidItemNo);
    public BidItemTO selectById(Integer bidItemNo);
    public Integer update(BidItemTO bidItemVo);

    }
