package com.polybrain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
/** 購物車的一筆商品；會放在 session 裡給購物車頁、結帳頁、確認頁顯示 */
public class CartItemImgDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer memNo;
    private Integer itemNo;
    private Integer quantity;
    private String itemName;
    private String itemImg;
    private Integer itemQty;
    private Integer itemPrice;
    private Integer quantityXitemPrice;
}
