package com.polybrain.repository.impl;

import com.polybrain.model.BidOrderDetailTO;
import com.polybrain.model.Item;
import com.polybrain.model.ItemOrderDetailTO;
import com.polybrain.model.ItemOrderTO;
import com.polybrain.model.MemTO;
import com.polybrain.repository.ItemOrderDAO;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;

import java.util.List;

public class ItemOrderDAOImpl implements ItemOrderDAO {

    /** 回傳資料庫產生的訂單編號 */
    public Integer insert(ItemOrderTO itemOrderVO) {
        getSession().persist(itemOrderVO);
        return itemOrderVO.getOrderNo();
    }

    public void deleteById(Integer orderNo) {
        Session session = getSession();
        session.remove(session.get(ItemOrderTO.class, orderNo));
    }

    public void deleteDetailById(Integer orderNo) {
        getSession().createQuery("DELETE FROM ItemOrderDetailTO WHERE orderNo = :orderNo")
                .setParameter("orderNo", orderNo)
                .executeUpdate();
    }

    public void addItemSales(Integer itemNo, Integer quantity) {
        getSession().createQuery("UPDATE Item SET itemSales = itemSales + :quantity WHERE itemNo = :itemNo")
                .setParameter("quantity", quantity)
                .setParameter("itemNo", itemNo)
                .executeUpdate();
    }

    /** SELECT ... FOR UPDATE 鎖住這個會員，同一個會員的結帳會排隊，直到 commit 才放開 */
    public MemTO lockMember(Integer memNo) {
        return getSession().get(MemTO.class, memNo, LockMode.PESSIMISTIC_WRITE);
    }

    /** 重新讀商品並 SELECT ... FOR UPDATE 鎖住，拿到的是別人 commit 後最新的庫存 */
    public Item lockItem(Integer itemNo) {
        Item item = getSession().get(Item.class, itemNo);
        getSession().refresh(item, new LockOptions(LockMode.PESSIMISTIC_WRITE));
        return item;
    }

    public ItemOrderTO selectById(Integer orderNo) {
        return getSession().get(ItemOrderTO.class, orderNo);
    }

    @Override
    public List<ItemOrderTO> selectAll() {
        return getSession().createQuery("FROM ItemOrderTO ORDER BY orderNo", ItemOrderTO.class).getResultList();
    }

    @Override
    public List<ItemOrderTO> selectByMemberNumber(Integer memNo) {
        return getSession().createQuery("FROM ItemOrderTO WHERE memNo = :memNo ORDER BY orderNo", ItemOrderTO.class)
                .setParameter("memNo", memNo)
                .getResultList();
    }

    @Override
    public void updateAnOrder(ItemOrderTO itemOrderVO) {
        getSession().update(itemOrderVO);
    }

    public void insertAnDetail(ItemOrderDetailTO itemOrderDetailVO) {
        getSession().persist(itemOrderDetailVO);
    }

    @Override
    public List<ItemOrderDetailTO> getDetailByOrderNumber(Integer orderNo) {
        return getSession().createQuery("FROM ItemOrderDetailTO WHERE orderNo = :orderNo ORDER BY itemNo", ItemOrderDetailTO.class)
                .setParameter("orderNo", orderNo)
                .getResultList();
    }

    public List<BidOrderDetailTO> findBidOrderDetailsByMemNo(Integer memNo) {
        return getSession().createQuery("FROM BidOrderDetailTO bod WHERE bod.bidOrderVo.memNo = :memNo", BidOrderDetailTO.class)
                .setParameter("memNo", memNo)
                .getResultList();
    }

    public Integer insertBidOrderDetail(BidOrderDetailTO bidOrderDetailVo) {
        getSession().persist(bidOrderDetailVo);
        return 1;
    }

    public List<BidOrderDetailTO> selectBidOrder() {
        return getSession().createQuery("FROM BidOrderDetailTO ORDER BY bod_no", BidOrderDetailTO.class).getResultList();
    }
}
