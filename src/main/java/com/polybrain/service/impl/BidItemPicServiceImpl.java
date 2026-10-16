package com.polybrain.service.impl;

import com.polybrain.repository.BidItemPicDao;
import com.polybrain.repository.impl.BidItemPicDaoImpl;
import com.polybrain.model.BidItemPicTO;
import com.polybrain.model.ItemImg;

import java.util.Base64;
import java.util.List;
import com.polybrain.service.BidItemPicService;

public class BidItemPicServiceImpl implements BidItemPicService {

    private BidItemPicDao bidItemPicDao;

    public BidItemPicServiceImpl() {
        bidItemPicDao = new BidItemPicDaoImpl();
    }

    @Override
    public void addBidItemPic(List<String> bidItemPic, Integer bidItemNo) {

        System.out.println("競標圖片新增");

        for (String base64String : bidItemPic) {
            base64String = base64String.substring(base64String.indexOf(",") + 1);
            byte[] file = Base64.getDecoder().decode(base64String);
            System.out.println(file);
            BidItemPicTO bidItemPicVo = new BidItemPicTO();
            bidItemPicVo.setBidItemPic(file);
            bidItemPicVo.setBidItemNo(bidItemNo);
            bidItemPicDao.insert(bidItemPicVo);

        }
    }

    @Override
    public boolean removeByBidItemNo(Integer bidItemNo) {
        System.out.println("刪除成功 (圖片)");
        return bidItemPicDao.deleteByBidItemNo(bidItemNo) > 0;
    }

    @Override
    public void editBidItemPics(List<String> bidItemPic, Integer bidItemNo) {
        System.out.println("編輯圖片");
        bidItemPicDao.deleteByBidItemNo(bidItemNo);
        for(String base64String : bidItemPic) {
            base64String = base64String.substring(base64String.indexOf(",") + 1);
            System.out.println(base64String);
            BidItemPicTO bidItemPicVo = new BidItemPicTO();
            byte[] img = Base64.getDecoder().decode(base64String);
            bidItemPicVo.setBidItemNo(bidItemNo);
            bidItemPicVo.setBidItemPic(img);
            final int resultCount = bidItemPicDao.update(bidItemPicVo);
        }
    }
}
