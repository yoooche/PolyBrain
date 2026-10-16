package com.polybrain.service;

import com.polybrain.repository.BidOrderDao;
import com.polybrain.repository.impl.BidOrderDaoImpl;
import com.polybrain.model.BidOrderDetailTO;
import com.polybrain.model.BidOrderTO;
import com.polybrain.service.CartTraceService;
import com.polybrain.dto.CartItemImgDTO;
import com.polybrain.service.impl.ItemServiceImpl;
import com.polybrain.model.Item;
import com.polybrain.repository.impl.ItemOrderDAOImpl;
import com.polybrain.model.ItemOrderDetailTO;
import com.polybrain.model.ItemOrderTO;
import com.polybrain.dto.OrderDetailDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private ItemOrderDAOImpl dao;
    private ItemServiceImpl ItemServiceDao;
    private CartTraceService cartTraceServiceDao;
    private BidOrderDao bidOrderDao;

    public OrderService() {
        dao = new ItemOrderDAOImpl();
    }

    public Integer addOrder(Integer memNo, Integer orderTotal, Integer orderState, String receiverName, String receiverAddress, String receiverPhone, Integer receiverMethod) {
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
        System.out.println("aaa");
        return dao.selectAll();
    }

    public ItemOrderTO updateBackOrder(ItemOrderTO itemordervo) {

//        OrderService odsvc = new OrderService();
//        System.out.println(itemordervo.getOrderNo());
//        ItemOrderTO itemordervoDB =dao.selectById(itemordervo.getOrderNo());
//        ItemOrderTO itemordervoDB = odsvc.getOneOrder(orderNo);
//        itemordervo.setOrderNo(itemordervo.getOrderNo());
//        itemordervo.setMemNo(itemordervo.getMemNo());
//        itemordervo.setReceiverAddress(itemordervo.getReceiverAddress());
//        itemordervo.setReceiverName(itemordervo.getReceiverName());
//        itemordervo.setReceiverPhone(itemordervo.getReceiverPhone());
//
//        itemordervo.setOrderTotal(itemordervoDB.getOrderTotal());
//        itemordervo.setReceiverMethod(itemordervoDB.getReceiverMethod());
//        itemordervo.setOrderState(itemordervoDB.getOrderState());
//        itemordervo.setTranTime(itemordervoDB.getTranTime());
//        System.out.println("jdbcgood");
        return dao.updateAnOrder(itemordervo);
    }

    public boolean deleteById(Integer orderNo) {
        try {
            dao.deleteDetailById(orderNo);
            dao.deleteById(orderNo) ;
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public Integer addAnOrderDetail(List<CartItemImgDTO> cartItemImgDTOList, Integer orderNo ,Integer memNo) {
        ItemServiceDao = new ItemServiceImpl();
        cartTraceServiceDao = new CartTraceService();
        for (int i = 0; i < cartItemImgDTOList.size(); i++) {


            ItemOrderDetailTO itemOrderDetailVO =
                    new ItemOrderDetailTO(orderNo,
                            cartItemImgDTOList.get(i).getItemNo(),
                            cartItemImgDTOList.get(i).getQuantity(),
                            cartItemImgDTOList.get(i).getItemPrice());
            dao.insertAnDetail(itemOrderDetailVO);

            Item item = new Item();
            item = ItemServiceDao.FindByItemId(cartItemImgDTOList.get(i).getItemNo());
            item.setItemPrice(item.getItemPrice());
            item.setItemProdDescription(item.getItemProdDescription());

            if (cartItemImgDTOList.get(i).getItemQty() - cartItemImgDTOList.get(i).getQuantity() == 0) {
                item.setItemState(2);
            }
            item.setItemQty(cartItemImgDTOList.get(i).getItemQty() - cartItemImgDTOList.get(i).getQuantity());

            ItemServiceDao.edit(item);
            System.out.println("商品總數更改成功");



            cartTraceServiceDao.deleteByMemItemNo(memNo, cartItemImgDTOList.get(i).getItemNo());
            System.out.println("哈囉" + cartItemImgDTOList.get(i).getItemNo());
            System.out.println("購物車刪除成功");

        }

        return 1;
    }

    public List<ItemOrderTO> selectFromMemNo(Integer memNo) {
        return dao.selectByMemberNumber(memNo);
    }

    public  List<OrderDetailDTO> selectOrderDetail(Integer orderNo){
        ItemServiceDao = new ItemServiceImpl();
        List<Item> itemList = new ArrayList<Item>();
        ItemOrderTO itemOrderVO = dao.selectById(orderNo);
        List<ItemOrderDetailTO> itemOrderDetailVOList = dao.getDetailByOrderNumber(orderNo);
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<OrderDetailDTO>();

        for (int i =0; i< itemOrderDetailVOList.size(); i++){
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            itemList.add(ItemServiceDao.FindByItemId(itemOrderDetailVOList.get(i).getItemNo()));

            orderDetailDTO.setItemOrderVO(itemOrderVO);
            orderDetailDTO.setItemSales(itemOrderDetailVOList.get(i).getItemSales());
            orderDetailDTO.setItemPrice(itemList.get(i).getItemPrice());
//            orderDetailDTO.setItemImg(itemList.get(i).getItemImg().get(0).getItemImg());
            orderDetailDTO.setItemName(itemList.get(i).getItemName());
            orderDetailDTOList.add(orderDetailDTO);
        }

        return orderDetailDTOList;
    }

    public Integer addBidOrderDetail (BidOrderDetailTO bidOrderDetailVo){
        return dao.insertBidOrderDetail(bidOrderDetailVo);
    }
    public BidOrderTO getBidOneOrder(Integer orderNo) {
        bidOrderDao = new BidOrderDaoImpl();
        return bidOrderDao.selectById(orderNo);
    }
    public List<BidOrderDetailTO> getBidAllOrder(){
        return dao.selectBidOrder();
    }
    public List<BidOrderDetailTO> getBidOrderByMem(Integer memNo){
        return dao.findBidOrderDetailsByMemNo(memNo);
    }



}
