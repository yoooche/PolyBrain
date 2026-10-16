package com.polybrain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/** 結帳表單填的收件資料；按「下一步」後存在 session，確認頁與確認下單時使用 */
@Getter
@Setter
public class CheckoutReceiverDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String receiverName;
    private String receiverPhone;
    private String county;
    private String district;
    private String receiverAddress;
    /** 表單的值：mail / storePickup */
    private String receiverMethod;
    /** 確認頁上給會員看的總金額；確認下單時重算不一樣就要求重新確認 */
    private Integer previewTotal;

    public String getFullAddress() {
        return county + district + receiverAddress;
    }

    public String getReceiverMethodText() {
        if ("mail".equals(receiverMethod)) {
            return "郵寄";
        }
        if ("storePickup".equals(receiverMethod)) {
            return "超商取貨";
        }
        return "";
    }
}
