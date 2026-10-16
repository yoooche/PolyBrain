package com.polybrain.bid.dao.impl;

import com.polybrain.common.util.HibernateUtil;
import com.polybrain.bid.entity.BidItemPicVo;
import com.polybrain.bid.entity.BidItemVo;
import com.polybrain.item.entity.Item;
import net.bytebuddy.asm.MemberSubstitution;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import com.polybrain.bid.dao.BidItemDao;
@Transactional
public class BidItemDaoImpl implements BidItemDao {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    @Override
    public Integer insert(BidItemVo bidItemVo) {
        session.persist(bidItemVo);
        return 1;
    }
    @Override
    public List<BidItemVo> selectAll() {
        final String hql = "FROM BidItemVo ORDER BY bidItemNo";
        return session
                .createQuery(hql, BidItemVo.class)
                .getResultList();
    }
    @Override
    public Integer deleteById(Integer bidItemNo) {
        BidItemVo bidItemVo = session.get(BidItemVo.class, bidItemNo);
        if (bidItemVo != null) {
            session.remove(bidItemVo);
            return 1;
        } else {
            return -1;
        }
    }
    @Override
    public BidItemVo selectById(Integer bidItemNo) {
        return session.get(BidItemVo.class, bidItemNo);
    }

    @Override
    public Integer update(BidItemVo bidItemVo) {
        try{
            session.merge(bidItemVo);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}
