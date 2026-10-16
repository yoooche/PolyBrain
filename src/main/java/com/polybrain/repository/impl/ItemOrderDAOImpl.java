package com.polybrain.repository.impl;

import com.polybrain.model.BidOrderDetailTO;
import com.polybrain.model.BidOrderTO;
import com.polybrain.model.ItemClass;
import com.polybrain.model.ItemOrderDetailTO;
import org.hibernate.Session;
import com.polybrain.repository.ItemOrderDAO;
import com.polybrain.model.ItemOrderTO;
import org.hibernate.query.Query;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.List;

public class ItemOrderDAOImpl implements ItemOrderDAO {
//    private static DataSource ds = null;
//
//    static {
//        try {
//            Context ctx = new InitialContext();
//            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/polybrain");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//    }

    public Integer insert(ItemOrderTO itemOrderVO) {
        getSession().persist(itemOrderVO);
        return itemOrderVO.getOrderNo();
    }

    public Integer deleteById(Integer orderNo) {
        Session session = getSession();
        ItemOrderTO pojo = session.get(ItemOrderTO.class, orderNo);
        session.remove(pojo);
        return 1;
    }

    public Integer deleteDetailById(Integer orderNo) {
        Session session = getSession();
        ItemOrderTO pojo = session.get(ItemOrderTO.class, orderNo);
        session.remove(pojo);
        return 1;
    }

    public Integer update(ItemOrderTO itemOrderVO) {
        return null;
    }

    public ItemOrderTO selectById(Integer orderNo) {
        return getSession().get(ItemOrderTO.class, orderNo);
    }

    @Override
    public List<ItemOrderTO> selectAll() {
        final String hql = "FROM ItemOrderTO ORDER BY ORDER_NO";
        return getSession().createQuery(hql, ItemOrderTO.class).getResultList();

    }


    public List<ItemOrderTO> selectByMemberNumber(Integer memNo) {
        final String hql = "FROM ItemOrderTO WHERE MEM_NO =" + memNo + "ORDER BY ORDER_NO";
        return getSession().createQuery(hql, ItemOrderTO.class).getResultList();
    }

    public ItemOrderTO updateAnOrder(ItemOrderTO itemordervo) {
        getSession().update(itemordervo);
        return itemordervo;
    }

    public void insertAnDetail(ItemOrderDetailTO itemOrderDetailVO) {
        getSession().persist(itemOrderDetailVO);
        System.out.println("新增訂單明細成功");
    }

    @Override
    public List<ItemOrderDetailTO> getDetailByOrderNumber(Integer orderNo) {
        final String hql = "FROM ItemOrderDetailTO WHERE order_No =" + orderNo + "ORDER BY ITEM_NO";
        return getSession().createQuery(hql, ItemOrderDetailTO.class).getResultList();
    }

    public List<BidOrderDetailTO> findBidOrderDetailsByMemNo(Integer memNo) {
        String hql = "FROM BidOrderDetailTO bod WHERE bod.bidOrderVo.memNo = :memNo";
        Query<BidOrderDetailTO> query = getSession().createQuery(hql, BidOrderDetailTO.class);
        return query.setParameter("memNo", memNo).getResultList();
    }

    public Integer insertBidOrderDetail(BidOrderDetailTO bidOrderDetailVo) {
        getSession().persist(bidOrderDetailVo);
        return 1 ;
    }
    public List<BidOrderDetailTO> selectBidOrder() {
        final String hql = "FROM BidOrderDetailTO ORDER BY bod_no";
        return getSession().createQuery(hql, BidOrderDetailTO.class).getResultList();
    }


    public static void main(String[] args) {
        BidOrderDetailTO bidOrderDetailVo = new BidOrderDetailTO();
        bidOrderDetailVo.setBidOrderNo(7002);
        bidOrderDetailVo.setReceiverPhone("0960888888");
        bidOrderDetailVo.setReceiverMethod(0);
        bidOrderDetailVo.setReceiverAddress("你要改喔");
        bidOrderDetailVo.setReceiverName("這麼狠喔");

        new ItemOrderDAOImpl().insertBidOrderDetail(bidOrderDetailVo);
    }
}
