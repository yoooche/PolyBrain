package com.polybrain.service;

import com.polybrain.dto.CartItemImgDTO;
import com.polybrain.dto.OrderDetailDTO;
import com.polybrain.model.BidOrderDetailTO;
import com.polybrain.model.BidOrderTO;
import com.polybrain.model.Item;
import com.polybrain.model.ItemOrderDetailTO;
import com.polybrain.model.ItemOrderTO;
import com.polybrain.model.MemTO;
import com.polybrain.repository.impl.BidOrderDaoImpl;
import com.polybrain.repository.impl.ItemOrderDAOImpl;
import com.polybrain.service.impl.ItemServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static com.polybrain.util.OrderUtil.ITEM_STATE_SOLD_OUT;

public class OrderService {
    private final ItemOrderDAOImpl dao = new ItemOrderDAOImpl();
    private final ItemServiceImpl itemService = new ItemServiceImpl();

    public Integer addOrder(Integer memNo, Integer orderTotal, Integer orderState, String receiverName,
                            String receiverAddress, String receiverPhone, Integer receiverMethod) {
        ItemOrderTO itemOrderVO = new ItemOrderTO();
        itemOrderVO.setMemNo(memNo);
        itemOrderVO.setOrderTotal(orderTotal);
        itemOrderVO.setOrderState(orderState);
        itemOrderVO.setReceiverName(receiverName);
        itemOrderVO.setReceiverAddress(receiverAddress);
        itemOrderVO.setReceiverPhone(receiverPhone);
        itemOrderVO.setReceiverMethod(receiverMethod);
        return dao.insert(itemOrderVO);
    }

    public ItemOrderTO getOneOrder(Integer orderNo) {
        return dao.selectById(orderNo);
    }

    public List<ItemOrderTO> getAll() {
        return dao.selectAll();
    }

    public void updateBackOrder(ItemOrderTO itemOrderVO) {
        dao.updateAnOrder(itemOrderVO);
    }

    /** 失敗會丟例外，讓 Filter rollback，不會只刪掉明細或只刪掉訂單 */
    public void deleteById(Integer orderNo) {
        dao.deleteDetailById(orderNo);
        dao.deleteById(orderNo);
    }

    /** 同一個會員同時只能有一筆結帳在跑；要在讀購物車之前呼叫，鎖到這次請求 commit 為止 */
    public MemTO lockMember(Integer memNo) {
        return dao.lockMember(memNo);
    }

    /**
     * 鎖住購物車裡每個商品的庫存，並把購物車上的庫存換成最新數字，之後再檢查庫存才準。
     * cartItems 要照商品編號排序（CartTraceDao.selectAll 已經排好）：大家照同樣順序上鎖，才不會互相等待卡死。
     */
    public void lockStock(List<CartItemImgDTO> cartItems) {
        for (CartItemImgDTO cartItem : cartItems) {
            cartItem.setItemQty(dao.lockItem(cartItem.getItemNo()).getItemQty());
        }
    }

    /** 寫入訂單明細、扣庫存、加銷售量，再把這些商品從購物車移除 */
    public void addAnOrderDetail(List<CartItemImgDTO> cartItems, Integer orderNo, Integer memNo) {
        CartTraceService cartTraceService = new CartTraceService();
        for (CartItemImgDTO cartItem : cartItems) {
            dao.insertAnDetail(new ItemOrderDetailTO(orderNo, cartItem.getItemNo(), cartItem.getQuantity(), cartItem.getItemPrice()));

            // 用資料庫現在的庫存來扣，不用購物車讀出來時的數字
            Item item = itemService.FindByItemId(cartItem.getItemNo());
            int remainQty = item.getItemQty() - cartItem.getQuantity();
            if (remainQty <= 0) {
                item.setItemState(ITEM_STATE_SOLD_OUT);
            }
            item.setItemQty(remainQty);
            itemService.edit(item);
            // Item.itemSales 是 updatable = false，只能另外用 HQL 加
            dao.addItemSales(item.getItemNo(), cartItem.getQuantity());

            cartTraceService.deleteByMemItemNo(memNo, cartItem.getItemNo());
        }
    }

    public List<ItemOrderTO> selectFromMemNo(Integer memNo) {
        return dao.selectByMemberNumber(memNo);
    }

    public List<OrderDetailDTO> selectOrderDetail(Integer orderNo) {
        ItemOrderTO itemOrderVO = dao.selectById(orderNo);
        List<OrderDetailDTO> orderDetails = new ArrayList<>();
        for (ItemOrderDetailTO detail : dao.getDetailByOrderNumber(orderNo)) {
            OrderDetailDTO orderDetail = new OrderDetailDTO();
            orderDetail.setItemOrderVO(itemOrderVO);
            orderDetail.setItemSales(detail.getItemSales());
            // 用下單當時記在明細上的價格，商品之後改價也不影響舊訂單
            orderDetail.setItemPrice(detail.getItemPrice());
            orderDetail.setItemName(itemService.FindByItemId(detail.getItemNo()).getItemName());
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    public Integer addBidOrderDetail(BidOrderDetailTO bidOrderDetailVo) {
        return dao.insertBidOrderDetail(bidOrderDetailVo);
    }

    public BidOrderTO getBidOneOrder(Integer orderNo) {
        return new BidOrderDaoImpl().selectById(orderNo);
    }

    public List<BidOrderDetailTO> getBidAllOrder() {
        return dao.selectBidOrder();
    }

    public List<BidOrderDetailTO> getBidOrderByMem(Integer memNo) {
        return dao.findBidOrderDetailsByMemNo(memNo);
    }
}
