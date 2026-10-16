package com.polybrain.repository;

import com.polybrain.model.CartTraceTO;

import java.util.List;

public interface CartTraceDAO extends CoreDao<CartTraceTO, Integer> {
    List<CartTraceTO> selectAll(Integer memNo);

    void updateQuantity(CartTraceTO cartTraceVO);

    void deleteByItemNo(Integer memNo, Integer itemNo);

    boolean insertToCart(CartTraceTO cartTraceVO);
}
