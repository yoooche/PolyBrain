package com.polybrain.bid.service;

import com.polybrain.common.service.CoreService;
import com.polybrain.bid.dto.BidItemListDto;
import com.polybrain.bid.dto.BidItemDto;
import com.polybrain.bid.entity.BidEventVo;
import com.polybrain.bid.entity.BidItemPicVo;
import com.polybrain.bid.entity.BidItemVo;
import com.polybrain.bid.entity.BidOrderVo;

import java.util.List;
import java.util.Map;

public interface BiddingService extends CoreService {

    // ========== about bidding item ==========
    List<BidItemVo> viewAll();
    public BidItemVo addAnItem(BidItemVo bidItemVo);
    public BidItemVo getOneItem(Integer bidItemNo);
    public boolean removeOneItem(Integer bidItemNo);
    List<String> viewAllName();
    public void addPics(BidItemPicVo bidItemPicVo);
    List<BidItemListDto> getHomePageList();
    List<BidItemDto> getTableData();
    List<String> selectAllPicsB64();
    BidItemVo edit(BidItemVo bidItemVo);

    // ========== about bidding event ==========
    List<BidEventVo> viewAllEvent();
    public void addAnEvent(BidEventVo bidEventVo);
    public BidEventVo getEventByNo(Integer bidEventNo);
    public Map<String, String> getStartTimeByNo(Integer bidEventNo);
    public void removeEventById(Integer bidEventNo);
    // ========== about bidding order ==========
    public void createOneOrder(Integer bidEventNo);
    public BidOrderVo orderWithoutBid(Integer bidEventNo, Integer memNo);
    public List<byte[]> getItemPicsByEveNo(Integer bidEventNo);
}
