package com.polybrain.service;

import com.polybrain.repository.CartTraceDAO;
import com.polybrain.repository.impl.CartTraceDAOimpl;
import com.polybrain.dto.CartItemImgDTO;
import com.polybrain.model.CartTraceTO;
import com.polybrain.repository.impl.ItemDAOimplPeter;
import com.polybrain.service.ItemService;
import com.polybrain.service.impl.ItemServiceImpl;
import com.polybrain.model.Item;
import org.apache.coyote.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class CartTraceService {
    private CartTraceDAO dao;

    public CartTraceService(){
        dao = new CartTraceDAOimpl();
    }
    public List<CartItemImgDTO> getAllCartItem(Integer memNo){
        CartTraceService cartTraceService = new CartTraceService();
        List<CartTraceTO> cartTraceVOList = new ArrayList<CartTraceTO>();
        List<Item> itemVONewList = new ArrayList<Item>();
        List<CartItemImgDTO> cartItemImgDTOList = new ArrayList<CartItemImgDTO>();

        cartTraceVOList = dao.selectAll(memNo); //用會員編號查商品編號 會員編號 數量的資訊

        ItemService itemService = new ItemServiceImpl();

        for (int i = 0; i < cartTraceVOList.size(); i++) { //會員編號 商品編號 數量

            if (cartTraceVOList.get(i).getQuantity() != 0) { //如果數量不是 0， 才能進來判斷
                CartItemImgDTO cartItemImgDTOS = new CartItemImgDTO();
                cartItemImgDTOS.setMemNo(cartTraceVOList.get(i).getMemNo());
                cartItemImgDTOS.setItemNo(cartTraceVOList.get(i).getItemNo());
                cartItemImgDTOS.setQuantity(cartTraceVOList.get(i).getQuantity());

                itemVONewList.add(itemService.FindByItemId(cartTraceVOList.get(i).getItemNo()));  //從迴圈拿到商品編號 套到selectAllByItemNo(Integer itemNo)的方法內

                cartItemImgDTOS.setItemName(itemVONewList.get(i).getItemName());
                cartItemImgDTOS.setItemImg(itemVONewList.get(i).getItemImg().get(0).getItemImg().toString());
                cartItemImgDTOS.setItemQty(itemVONewList.get(i).getItemQty());
                cartItemImgDTOS.setItemPrice(itemVONewList.get(i).getItemPrice());
                cartItemImgDTOS.setQuantityXitemPrice(cartTraceVOList.get(i).getQuantity() * itemVONewList.get(i).getItemPrice());
                cartItemImgDTOList.add(cartItemImgDTOS);
            }
            else {
                cartTraceService.deleteByMemItemNo(memNo, cartTraceVOList.get(i).getItemNo());
                cartTraceVOList.remove(i);
            }


        }
        return cartItemImgDTOList;
    }

    public Integer deleteByMemItemNo(Integer memNo, Integer itemNo){
        dao.deleteByItemNo(memNo, itemNo);
        return 1;
    }

    public Integer updateArow(CartTraceTO cartTraceVO){
        try {
            dao.updateQuantity(cartTraceVO);
            return 1;
        }catch(Exception e){
            return -1;
        }

    }

    public Boolean addToCart(CartTraceTO cartTraceVO){
       Integer memno = cartTraceVO.getMemNo();
        System.out.println("這是會員編號"+memno);
       Integer ItemNo = cartTraceVO.getItemNo();
        System.out.println("這是產品編號"+ItemNo);
        Integer Qty = cartTraceVO.getQuantity();
        System.out.println("這是數量"+Qty);
        return dao.insertToCart(cartTraceVO) > 0;
    }


}
