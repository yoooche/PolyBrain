package com.polybrain.repository.impl;

import com.polybrain.util.HibernateUtil;
import com.polybrain.model.BidEventTO;
import com.polybrain.model.BidItemTO;
import org.hibernate.Session;

import java.util.List;
import com.polybrain.repository.BidEventDao;

public class BidEventDaoImpl implements BidEventDao{
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    @Override
    public void insert(BidEventTO bidEventVo) {
        session.persist(bidEventVo);
    }

    @Override
    public List<BidEventTO> selectAll() {
        final String hql = "FROM BidEventTO ORDER BY bidEventNo";
        return session
                .createQuery(hql, BidEventTO.class)
                .getResultList();
    }

    @Override
    public void deleteById(Integer bidEventNo) {
        BidEventTO bidEventVo = session.get(BidEventTO.class, bidEventNo);
        session.remove(bidEventVo);
    }

    @Override
    public BidEventTO selectById(Integer bidEventNo) {
        return session.get(BidEventTO.class, bidEventNo);
    }

    @Override
    public String selectItemNameByEveNo(Integer bidEventNo) {
        return session.get(BidEventTO.class, bidEventNo).getBidItemVo().getBidItemName();
    }

    @Override
    public Integer selectItemNoByEveNo(Integer bidEventNo) {
        return session.get(BidEventTO.class, bidEventNo).getBidItemVo().getBidItemNo();
    }
}
