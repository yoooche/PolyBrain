package com.polybrain.cart.dao;

import com.polybrain.common.dao.CoreDao;
import com.polybrain.cart.entity.CartTraceVO;

import java.util.List;

public interface CartTraceDAO extends CoreDao<CartTraceVO, Integer> {
    CartTraceVO selectByMemberNumber(Integer memNo);

    List<CartTraceVO> selectAll(Integer memNo);

    Integer updateQuantity(CartTraceVO cartTraceVO);

    Integer deleteByItemNo(Integer memNo ,Integer itemNo );
    Integer insertToCart(CartTraceVO cartTraceVO);

}
