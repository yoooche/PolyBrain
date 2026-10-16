package com.polybrain.repository.impl;

import com.polybrain.model.CartTraceTO;
import com.polybrain.repository.CartTraceDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/** 都用 LoginFilter 開好的 transaction，呼叫端要掛在 /loginRequired/* 底下 */
public class CartTraceDaoImpl implements CartTraceDAO {
    private static final Logger logger = LogManager.getLogger(CartTraceDaoImpl.class);

    @Override
    public List<CartTraceTO> selectAll(Integer memNo) {
        return getSession()
                .createQuery("FROM CartTraceTO WHERE memNo = :memNo ORDER BY itemNo", CartTraceTO.class)
                .setParameter("memNo", memNo)
                .getResultList();
    }

    /** CoreDao 規定要有，購物車不會查全部會員的資料 */
    @Override
    public List<CartTraceTO> selectAll() {
        throw new UnsupportedOperationException("Use selectAll(memNo)");
    }

    @Override
    public void updateQuantity(CartTraceTO cartTraceVO) {
        getSession().merge(cartTraceVO);
    }

    @Override
    public void deleteByItemNo(Integer memNo, Integer itemNo) {
        getSession()
                .createQuery("DELETE FROM CartTraceTO WHERE memNo = :memNo AND itemNo = :itemNo")
                .setParameter("memNo", memNo)
                .setParameter("itemNo", itemNo)
                .executeUpdate();
    }

    /**
     * 購物車已經有這個商品就把數量加上去，沒有就新增一筆。
     * 用一句 SQL 做完：先查再決定新增或更新的話，同時加兩次會互相蓋掉數量。
     */
    @Override
    public boolean insertToCart(CartTraceTO cartTraceVO) {
        try {
            getSession()
                    .createNativeQuery("INSERT INTO CART (MEM_NO, ITEM_NO, quantity) VALUES (:memNo, :itemNo, :quantity)"
                            + " ON DUPLICATE KEY UPDATE quantity = quantity + :quantity")
                    .setParameter("memNo", cartTraceVO.getMemNo())
                    .setParameter("itemNo", cartTraceVO.getItemNo())
                    .setParameter("quantity", cartTraceVO.getQuantity())
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            logger.error("Failed to add item {} to cart of member {}", cartTraceVO.getItemNo(), cartTraceVO.getMemNo(), e);
            return false;
        }
    }
}
