package com.polybrain.controller;

import com.polybrain.model.Core;
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

import static com.polybrain.util.CommonUtil.json2Pojo;
import static com.polybrain.util.CommonUtil.writePojo2Json;

@WebServlet("/general/bidItemRemove")
public class BidItemRemove extends HttpServlet {

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

        Integer bidItemNo = json2Pojo(req, BidItemTO.class).getBidItemNo();
        System.out.println("Test");
        Core core = new Core();
        if (bidItemNo == null) {
            core.setMessage("無該產品編號");
            core.setSuccess(false);
        } else {
            core.setSuccess(biddingService.removeOneItem(bidItemNo));
            core.setSuccess(bidItemPicService.removeByBidItemNo(bidItemNo));
            System.out.println(core);
        }
        writePojo2Json(resp, core);
    }
}
