package com.polybrain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "ITEM_ORDER")
public class ItemOrderTO extends Core {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_NO")
    private Integer orderNo;
    @Column(name = "MEM_NO")
    private Integer memNo;
    // 由資料庫預設值填入下單日期
    @Column(name = "TRAN_TIME", insertable = false)
    private Date tranTime;
    @Column(name = "ORDER_TOTAL", updatable = false)
    private Integer orderTotal;
    @Column(name = "ORDER_STATE")
    private Integer orderState;
    @Column(name = "RECEIVER_NAME")
    private String receiverName;
    @Column(name = "RECEIVER_ADDRESS")
    private String receiverAddress;
    @Column(name = "RECEIVER_PHONE")
    private String receiverPhone;
    @Column(name = "RECEIVER_METHOD")
    private Integer receiverMethod;
}
