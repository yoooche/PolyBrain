package com.polybrain.controller;


import com.polybrain.model.Core;
import com.polybrain.util.CommonUtil;
import com.polybrain.service.ItemImgService;
import com.polybrain.service.impl.ItemImgServiceImpl;
import com.polybrain.service.ItemService;
import com.polybrain.service.impl.ItemServiceImpl;
import com.polybrain.model.Item;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.polybrain.util.CommonUtil.json2Pojo;

    @WebServlet("/general/item/remove")
    public class ItemRemoveServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;
        private ItemService service = new ItemServiceImpl();
        private ItemImgService service2 = new ItemImgServiceImpl();
        CommonUtil commonUtil = new CommonUtil();
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) {
            final Integer id = json2Pojo(request, Item.class).getItemNo();
            final Core core = new Core();
            if (id == null) {
                core.setMessage("無該產品編號");
                core.setSuccess(false);
            } else {
                core.setSuccess(service.remove(id));
                core.setSuccess(service2.removeImg(id));
            }
            commonUtil.writePojo2Json(response, core);
        }
}
