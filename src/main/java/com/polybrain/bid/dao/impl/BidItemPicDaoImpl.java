package com.polybrain.bid.dao.impl;

import com.polybrain.common.util.HibernateUtil;
import com.polybrain.bid.entity.BidItemPicVo;
import com.polybrain.item.entity.ItemImg;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import com.polybrain.bid.dao.BidItemPicDao;

public class BidItemPicDaoImpl implements BidItemPicDao {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    @Override
    public void insert(BidItemPicVo bidItemPicVo) {
        session.persist(bidItemPicVo);
    }

    @Override
    public List<byte[]> selectPicsById(Integer bidItemNo) {
        String hql = "SELECT bidItemPic FROM BidItemPicVo WHERE bidItemNo = :bidItemNo";
        Query<byte[]> query = session.createQuery(hql, byte[].class);
        query.setParameter("bidItemNo", bidItemNo);
        return query.getResultList();
    }

    @Override
    public List<BidItemPicVo> selectAllPics() {
        final String hql = "FROM BidItemPicVo ORDER BY bidItemPicNo";
        return session.createQuery(hql, BidItemPicVo.class).getResultList();
    }

    @Override
    public Integer deleteByBidItemNo(Integer bidItemNo) {
        System.out.println("圖片有刪掉嗎?");
        final String sql = "DELETE FROM bid_item_pic WHERE bid_item_no = :bidItemNo";
        return session.createNativeQuery(sql)
                .setParameter("bidItemNo", bidItemNo)
                .executeUpdate();
    }
    @Override
    public Integer update(BidItemPicVo bidItemPicVo){
        try{
            session.merge(bidItemPicVo);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }


}