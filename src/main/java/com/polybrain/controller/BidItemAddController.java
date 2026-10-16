package com.polybrain.controller;

import com.polybrain.model.Core;
import com.polybrain.util.CommonUtil;
import com.polybrain.dto.BidItemDto;
import com.polybrain.service.BidItemPicService;
import com.polybrain.service.impl.BidItemPicServiceImpl;
import com.polybrain.service.BiddingService;
import com.polybrain.service.impl.BiddingServiceImpl;
import com.polybrain.model.BidItemTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.polybrain.util.CommonUtil.json2Pojo;

@WebServlet("/general/BidItemAdd")
public class BidItemAddController extends HttpServlet {
    private BidItemPicService bidItemPicService;
    private BiddingService biddingService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        bidItemPicService = new BidItemPicServiceImpl();
        biddingService = new BiddingServiceImpl();
        System.out.println(req);

        BidItemDto bidItemDto = json2Pojo(req, BidItemDto.class);
        System.out.println(bidItemDto);
        BidItemTO bidItem = bidItemDto.getBidItemVo();
        System.out.println(bidItem);
        List<String> bidItemPic = bidItemDto.getBidItemPic();
        System.out.println(bidItemPic);

        if (bidItem == null) {
            bidItem = new BidItemTO();
            bidItem.setMessage("無商品資訊");
            bidItem.setSuccess(false);
            CommonUtil.writePojo2Json(resp, bidItem);
            return;
        }
        if (bidItem.getBidItemNo() == null) {
            System.out.println("新增商品");
            BidItemTO bidItemVoAdd = biddingService.addAnItem(bidItem);
            Integer bidItemVoAddNo = bidItemVoAdd.getBidItemNo();
            System.out.println("新商品 = " + bidItemVoAdd);
            System.out.println("商品序號 = " + bidItemVoAddNo);
            bidItemPicService.addBidItemPic(bidItemPic, bidItemVoAddNo);
            CommonUtil.writePojo2Json(resp, bidItemVoAdd);
            return;
        }
        if(bidItem.getBidItemNo() != null){
            System.out.println("修改商品");
            bidItem = biddingService.edit(bidItem);
            if(!bidItemPic.isEmpty()){
                System.out.println("有圖片需要修改");
                Integer bidItemNo = bidItem.getBidItemNo();
                bidItemPicService.editBidItemPics(bidItemPic, bidItemNo);
            }
        }
    }
}
