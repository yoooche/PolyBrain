package com.polybrain.util;

import com.polybrain.dto.CartItemImgDTO;
import com.polybrain.dto.CheckoutReceiverDTO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** 購物車、訂單共用的規則；不碰資料庫，方便寫單元測試 */
public class OrderUtil {
    public static final int ORDER_STATE_NEW = 0;
    public static final int RECEIVER_METHOD_MAIL = 0;
    public static final int RECEIVER_METHOD_STORE_PICKUP = 1;
    public static final int ITEM_STATE_SOLD_OUT = 2;
    public static final String PHONE_REGEX = "^09[0-9]{8}$";

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches(PHONE_REGEX);
    }

    /** 結帳表單的寄送方式轉成資料庫的值；不認得的回 null */
    public static Integer toReceiverMethod(String method) {
        if ("mail".equals(method)) {
            return RECEIVER_METHOD_MAIL;
        }
        if ("storePickup".equals(method)) {
            return RECEIVER_METHOD_STORE_PICKUP;
        }
        return null;
    }

    /** 檢查結帳表單的收件資料；key 是欄位名稱，對應 CartTrace.jsp 上顯示錯誤的位置，沒錯就回空的 */
    public static Map<String, String> validateReceiver(CheckoutReceiverDTO receiver) {
        Map<String, String> errorMsgs = new LinkedHashMap<>();
        if (receiver.getReceiverName().isEmpty()) {
            errorMsgs.put("receiverName", "請填寫收件人姓名");
        }
        if (receiver.getReceiverPhone().isEmpty()) {
            errorMsgs.put("receiverPhone", "收件人電話不為空");
        } else if (!isValidPhone(receiver.getReceiverPhone())) {
            errorMsgs.put("receiverPhone", "手機號碼格式不正確");
        }
        if (receiver.getCounty().isEmpty() || receiver.getDistrict().isEmpty()) {
            errorMsgs.put("receiverAddress", "請選擇縣市和地區");
        } else if (receiver.getReceiverAddress().isEmpty()) {
            errorMsgs.put("receiverAddress", "請填寫收件地址");
        }
        if (toReceiverMethod(receiver.getReceiverMethod()) == null) {
            errorMsgs.put("receiverMethod", "你沒有選擇寄送方式喔! ");
        }
        return errorMsgs;
    }

    public static boolean canAddToCart(Integer quantity, Integer stock) {
        return quantity != null && stock != null && quantity > 0 && quantity <= stock;
    }

    public static int calcOrderTotal(List<CartItemImgDTO> cartItems) {
        int total = 0;
        for (CartItemImgDTO cartItem : cartItems) {
            total += cartItem.getQuantity() * cartItem.getItemPrice();
        }
        return total;
    }

    /** 回傳第一個數量超過庫存的商品，都夠就回 null */
    public static CartItemImgDTO findOutOfStock(List<CartItemImgDTO> cartItems) {
        for (CartItemImgDTO cartItem : cartItems) {
            if (cartItem.getQuantity() > cartItem.getItemQty()) {
                return cartItem;
            }
        }
        return null;
    }
}
