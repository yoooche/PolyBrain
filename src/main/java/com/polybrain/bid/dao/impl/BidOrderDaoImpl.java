package com.polybrain.bid.dao.impl;

import com.polybrain.common.util.HibernateUtil;
import com.polybrain.bid.entity.BidOrderVo;
import org.hibernate.Session;

import java.util.List;
import com.polybrain.bid.dao.BidOrderDao;

public class BidOrderDaoImpl implements BidOrderDao{
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    @Override
    public BidOrderVo insert(BidOrderVo bidOrderVo) {
        session.persist(bidOrderVo);
        return bidOrderVo;
    }
    public BidOrderVo selectById(Integer bidOrderNo){
        return session.get(BidOrderVo.class, bidOrderNo);
    }

    public static void main(String[] args) {
        System.out.println("很棒吧: " + new BidOrderDaoImpl().selectById(7001));
    }
}
