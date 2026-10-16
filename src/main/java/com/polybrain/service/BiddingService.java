package com.polybrain.service;

import com.polybrain.service.CoreService;
import com.polybrain.dto.BidItemListDto;
import com.polybrain.dto.BidItemDto;
import com.polybrain.model.BidEventTO;
import com.polybrain.model.BidItemPicTO;
import com.polybrain.model.BidItemTO;
import com.polybrain.model.BidOrderTO;

import java.util.List;
import java.util.Map;

public interface BiddingService extends CoreService {

    // ========== about bidding item ==========
    List<BidItemTO> viewAll();
    public BidItemTO addAnItem(BidItemTO bidItemVo);
    public BidItemTO getOneItem(Integer bidItemNo);
    public boolean removeOneItem(Integer bidItemNo);
    List<String> viewAllName();
    public void addPics(BidItemPicTO bidItemPicVo);
    List<BidItemListDto> getHomePageList();
    List<BidItemDto> getTableData();
    List<String> selectAllPicsB64();
    BidItemTO edit(BidItemTO bidItemVo);

    // ========== about bidding event ==========
    List<BidEventTO> viewAllEvent();
    public void addAnEvent(BidEventTO bidEventVo);
    public BidEventTO getEventByNo(Integer bidEventNo);
    public Map<String, String> getStartTimeByNo(Integer bidEventNo);
    public void removeEventById(Integer bidEventNo);
    // ========== about bidding order ==========
    public void createOneOrder(Integer bidEventNo);
    public BidOrderTO orderWithoutBid(Integer bidEventNo, Integer memNo);
    public List<byte[]> getItemPicsByEveNo(Integer bidEventNo);
}
