package com.polybrain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CART")
@IdClass(CartTraceId.class)
public class CartTraceTO {
    @Id
    @Column(name = "MEM_NO", updatable = false)
    private Integer memNo;
    @Id
    @Column(name = "ITEM_NO", updatable = false)
    private Integer itemNo;
    @Column(name = "quantity")
    private Integer quantity;
}
