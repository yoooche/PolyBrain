package com.polybrain.repository;

import com.polybrain.repository.CoreDao;
import com.polybrain.model.CartTraceTO;

import java.util.List;

public interface CartTraceDAO extends CoreDao<CartTraceTO, Integer> {
    CartTraceTO selectByMemberNumber(Integer memNo);

    List<CartTraceTO> selectAll(Integer memNo);

    Integer updateQuantity(CartTraceTO cartTraceVO);

    Integer deleteByItemNo(Integer memNo ,Integer itemNo );
    Integer insertToCart(CartTraceTO cartTraceVO);

}
