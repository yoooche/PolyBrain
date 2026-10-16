package com.polybrain.bid.dao.impl;

import com.polybrain.common.util.HibernateUtil;
import com.polybrain.bid.entity.BidEventVo;
import com.polybrain.bid.entity.BidItemVo;
import org.hibernate.Session;

import java.util.List;
import com.polybrain.bid.dao.BidEventDao;

public class BidEventDaoImpl implements BidEventDao{
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    @Override
    public void insert(BidEventVo bidEventVo) {
        session.persist(bidEventVo);
    }

    @Override
    public List<BidEventVo> selectAll() {
        final String hql = "FROM BidEventVo ORDER BY bidEventNo";
        return session
                .createQuery(hql, BidEventVo.class)
                .getResultList();
    }

    @Override
    public void deleteById(Integer bidEventNo) {
        BidEventVo bidEventVo = session.get(BidEventVo.class, bidEventNo);
        session.remove(bidEventVo);
    }

    @Override
    public BidEventVo selectById(Integer bidEventNo) {
        return session.get(BidEventVo.class, bidEventNo);
    }

    @Override
    public String selectItemNameByEveNo(Integer bidEventNo) {
        return session.get(BidEventVo.class, bidEventNo).getBidItemVo().getBidItemName();
    }

    @Override
    public Integer selectItemNoByEveNo(Integer bidEventNo) {
        return session.get(BidEventVo.class, bidEventNo).getBidItemVo().getBidItemNo();
    }
}
