package com.polybrain.item.controller;


import com.polybrain.common.entity.Core;
import com.polybrain.common.util.CommonUtil;
import com.polybrain.item.service.ItemClassService;
import com.polybrain.item.service.impl.ItemClassServiceImpl;
import com.polybrain.item.entity.ItemClass;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.polybrain.common.util.CommonUtil.json2Pojo;

@WebServlet("/general/item/ClassRemove")
public class ItemRemoveClassServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ItemClassService service = new ItemClassServiceImpl();
    CommonUtil commonUtil = new CommonUtil();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        final Integer id = json2Pojo(request, ItemClass.class).getItemClassNo();
        final Core core = new Core();
        if (id == null) {
            core.setMessage("無該產品編號");
            core.setSuccess(false);
        } else {
            core.setSuccess(service.remove(id));
        }
        commonUtil.writePojo2Json(response, core);
    }
}
