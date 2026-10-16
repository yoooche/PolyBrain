package com.polybrain.service;

import com.polybrain.dto.CartItemImgDTO;
import com.polybrain.model.CartTraceTO;
import com.polybrain.model.Item;
import com.polybrain.repository.CartTraceDAO;
import com.polybrain.repository.impl.CartTraceDaoImpl;
import com.polybrain.service.impl.ItemServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.polybrain.util.OrderUtil.canAddToCart;

public class CartTraceService {
    private static final Logger logger = LogManager.getLogger(CartTraceService.class);

    private final CartTraceDAO dao = new CartTraceDaoImpl();
    private final ItemService itemService = new ItemServiceImpl();

    /** 會員的購物車，每筆附上商品名稱、圖片、庫存、單價；數量是 0 的會順便從購物車刪掉 */
    public List<CartItemImgDTO> getAllCartItem(Integer memNo) {
        List<CartItemImgDTO> cartItems = new ArrayList<>();
        for (CartTraceTO cartTraceVO : dao.selectAll(memNo)) {
            if (cartTraceVO.getQuantity() == 0) {
                deleteByMemItemNo(memNo, cartTraceVO.getItemNo());
                continue;
            }
            Item item = itemService.FindByItemId(cartTraceVO.getItemNo());

            CartItemImgDTO cartItem = new CartItemImgDTO();
            cartItem.setMemNo(cartTraceVO.getMemNo());
            cartItem.setItemNo(cartTraceVO.getItemNo());
            cartItem.setQuantity(cartTraceVO.getQuantity());
            cartItem.setItemName(item.getItemName());
            cartItem.setItemImg(item.getItemImg().get(0).getItemImg());
            cartItem.setItemQty(item.getItemQty());
            cartItem.setItemPrice(item.getItemPrice());
            cartItem.setQuantityXitemPrice(cartTraceVO.getQuantity() * item.getItemPrice());
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    public void deleteByMemItemNo(Integer memNo, Integer itemNo) {
        dao.deleteByItemNo(memNo, itemNo);
    }

    public void updateArow(CartTraceTO cartTraceVO) {
        dao.updateQuantity(cartTraceVO);
    }

    public boolean addToCart(CartTraceTO cartTraceVO) {
        Item item = itemService.FindByItemId(cartTraceVO.getItemNo());
        Integer stock = item == null ? null : item.getItemQty();
        if (!canAddToCart(cartTraceVO.getQuantity(), stock)) {
            logger.info("Member {} cannot add item {} x{} to cart: out of stock",
                    cartTraceVO.getMemNo(), cartTraceVO.getItemNo(), cartTraceVO.getQuantity());
            return false;
        }
        return dao.insertToCart(cartTraceVO);
    }
}
