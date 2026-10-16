package com.polybrain.util;

import com.polybrain.dto.CartItemImgDTO;
import com.polybrain.dto.CheckoutReceiverDTO;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class OrderUtilTest {

    private static CartItemImgDTO cartItem(String name, int quantity, int stock, int price) {
        CartItemImgDTO item = new CartItemImgDTO();
        item.setItemName(name);
        item.setQuantity(quantity);
        item.setItemQty(stock);
        item.setItemPrice(price);
        return item;
    }

    @Test
    public void isValidPhone_mobileNumber_true() {
        assertTrue(OrderUtil.isValidPhone("0912345678"));
    }

    @Test
    public void isValidPhone_wrongFormat_false() {
        assertFalse(OrderUtil.isValidPhone("12345"));
        assertFalse(OrderUtil.isValidPhone("0812345678"));
        assertFalse(OrderUtil.isValidPhone("091234567"));
        assertFalse(OrderUtil.isValidPhone("0912-345-678"));
        assertFalse(OrderUtil.isValidPhone(null));
    }

    @Test
    public void toReceiverMethod_knownValues() {
        assertEquals(Integer.valueOf(OrderUtil.RECEIVER_METHOD_MAIL), OrderUtil.toReceiverMethod("mail"));
        assertEquals(Integer.valueOf(OrderUtil.RECEIVER_METHOD_STORE_PICKUP), OrderUtil.toReceiverMethod("storePickup"));
    }

    @Test
    public void toReceiverMethod_defaultOptionOrUnknown_null() {
        assertNull(OrderUtil.toReceiverMethod("default"));
        assertNull(OrderUtil.toReceiverMethod(""));
        assertNull(OrderUtil.toReceiverMethod(null));
    }

    @Test
    public void canAddToCart_withinStock_true() {
        assertTrue(OrderUtil.canAddToCart(1, 5));
        assertTrue(OrderUtil.canAddToCart(5, 5));
    }

    @Test
    public void canAddToCart_soldOutOrTooMany_false() {
        assertFalse(OrderUtil.canAddToCart(1, 0));
        assertFalse(OrderUtil.canAddToCart(6, 5));
    }

    @Test
    public void canAddToCart_invalidQuantityOrUnknownItem_false() {
        assertFalse(OrderUtil.canAddToCart(0, 5));
        assertFalse(OrderUtil.canAddToCart(-1, 5));
        assertFalse(OrderUtil.canAddToCart(null, 5));
        assertFalse(OrderUtil.canAddToCart(1, null));
    }

    @Test
    public void calcOrderTotal_sumsQuantityTimesPrice() {
        List<CartItemImgDTO> cart = Arrays.asList(
                cartItem("卡坦島", 2, 10, 1280),
                cartItem("機密代號", 1, 10, 650));
        assertEquals(2 * 1280 + 650, OrderUtil.calcOrderTotal(cart));
    }

    @Test
    public void calcOrderTotal_emptyCart_zero() {
        assertEquals(0, OrderUtil.calcOrderTotal(Collections.emptyList()));
    }

    @Test
    public void findOutOfStock_allEnough_null() {
        List<CartItemImgDTO> cart = Arrays.asList(
                cartItem("卡坦島", 2, 2, 1280),
                cartItem("機密代號", 1, 30, 650));
        assertNull(OrderUtil.findOutOfStock(cart));
    }

    @Test
    public void findOutOfStock_returnsFirstShortItem() {
        CartItemImgDTO shortItem = cartItem("鐵道任務", 3, 1, 1500);
        List<CartItemImgDTO> cart = Arrays.asList(
                cartItem("卡坦島", 1, 10, 1280),
                shortItem,
                cartItem("拼布對決", 2, 0, 890));
        assertSame(shortItem, OrderUtil.findOutOfStock(cart));
    }

    private static CheckoutReceiverDTO validReceiver() {
        CheckoutReceiverDTO receiver = new CheckoutReceiverDTO();
        receiver.setReceiverName("王小明");
        receiver.setReceiverPhone("0912345678");
        receiver.setCounty("台北市");
        receiver.setDistrict("中正區");
        receiver.setReceiverAddress("忠孝西路一段 1 號");
        receiver.setReceiverMethod("mail");
        return receiver;
    }

    @Test
    public void validateReceiver_allFilled_noError() {
        assertTrue(OrderUtil.validateReceiver(validReceiver()).isEmpty());
    }

    @Test
    public void validateReceiver_emptyForm_errorOnEveryField() {
        CheckoutReceiverDTO receiver = new CheckoutReceiverDTO();
        receiver.setReceiverName("");
        receiver.setReceiverPhone("");
        receiver.setCounty("");
        receiver.setDistrict("");
        receiver.setReceiverAddress("");
        receiver.setReceiverMethod("default");

        Map<String, String> errors = OrderUtil.validateReceiver(receiver);
        assertEquals("請填寫收件人姓名", errors.get("receiverName"));
        assertEquals("收件人電話不為空", errors.get("receiverPhone"));
        assertEquals("請選擇縣市和地區", errors.get("receiverAddress"));
        assertEquals("你沒有選擇寄送方式喔! ", errors.get("receiverMethod"));
    }

    @Test
    public void validateReceiver_badPhone_formatError() {
        CheckoutReceiverDTO receiver = validReceiver();
        receiver.setReceiverPhone("12345");
        Map<String, String> errors = OrderUtil.validateReceiver(receiver);
        assertEquals(1, errors.size());
        assertEquals("手機號碼格式不正確", errors.get("receiverPhone"));
    }

    @Test
    public void validateReceiver_districtPickedButNoStreet_addressError() {
        CheckoutReceiverDTO receiver = validReceiver();
        receiver.setReceiverAddress("");
        assertEquals("請填寫收件地址", OrderUtil.validateReceiver(receiver).get("receiverAddress"));
    }

    @Test
    public void receiverDto_fullAddressAndMethodText() {
        CheckoutReceiverDTO receiver = validReceiver();
        assertEquals("台北市中正區忠孝西路一段 1 號", receiver.getFullAddress());
        assertEquals("郵寄", receiver.getReceiverMethodText());
        receiver.setReceiverMethod("storePickup");
        assertEquals("超商取貨", receiver.getReceiverMethodText());
    }
}
