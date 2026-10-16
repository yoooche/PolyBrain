package com.polybrain.repository.impl;

import com.polybrain.util.HibernateUtil;
import com.polybrain.model.BidOrderTO;
import org.hibernate.Session;

import java.util.List;
import com.polybrain.repository.BidOrderDao;

public class BidOrderDaoImpl implements BidOrderDao{
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    @Override
    public BidOrderTO insert(BidOrderTO bidOrderVo) {
        session.persist(bidOrderVo);
        return bidOrderVo;
    }
    public BidOrderTO selectById(Integer bidOrderNo){
        return session.get(BidOrderTO.class, bidOrderNo);
    }

    public static void main(String[] args) {
        System.out.println("很棒吧: " + new BidOrderDaoImpl().selectById(7001));
    }
}
