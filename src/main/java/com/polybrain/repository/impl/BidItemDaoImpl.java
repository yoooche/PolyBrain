package com.polybrain.repository.impl;

import com.polybrain.util.HibernateUtil;
import com.polybrain.model.BidItemPicTO;
import com.polybrain.model.BidItemTO;
import com.polybrain.model.Item;
import net.bytebuddy.asm.MemberSubstitution;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import com.polybrain.repository.BidItemDao;
@Transactional
public class BidItemDaoImpl implements BidItemDao {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    @Override
    public Integer insert(BidItemTO bidItemVo) {
        session.persist(bidItemVo);
        return 1;
    }
    @Override
    public List<BidItemTO> selectAll() {
        final String hql = "FROM BidItemTO ORDER BY bidItemNo";
        return session
                .createQuery(hql, BidItemTO.class)
                .getResultList();
    }
    @Override
    public Integer deleteById(Integer bidItemNo) {
        BidItemTO bidItemVo = session.get(BidItemTO.class, bidItemNo);
        if (bidItemVo != null) {
            session.remove(bidItemVo);
            return 1;
        } else {
            return -1;
        }
    }
    @Override
    public BidItemTO selectById(Integer bidItemNo) {
        return session.get(BidItemTO.class, bidItemNo);
    }

    @Override
    public Integer update(BidItemTO bidItemVo) {
        try{
            session.merge(bidItemVo);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}
