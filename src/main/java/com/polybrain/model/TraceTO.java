package com.polybrain.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TraceTO {
        private String itemImg;
        private String itemName;
        private int itemNo;
        private int itemPrice;
    @Override
    public String toString() {
        return "TraceTO{" +
                "itemNo=" + itemNo +
                ", itemImg='" + itemImg + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                '}';
    }
    }

