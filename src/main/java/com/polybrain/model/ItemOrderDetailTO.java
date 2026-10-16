package com.polybrain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(OrderDetailId.class)
@Table(name = "ORDER_DETAIL")
public class ItemOrderDetailTO {
        @Id
        @Column(name="ORDER_NO" ,insertable = false,updatable = false)
        private Integer orderNo;
        @Id
        @Column(name="ITEM_NO", insertable = false, updatable = false)
        private Integer itemNo;
        @Column(name="ITEM_SALES")
        private Integer itemSales;
        @Column(name="ITEM_PRICE")
        private Integer itemPrice;
}
class OrderDetailId implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer orderNo;
        private Integer itemNo;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                OrderDetailId that = (OrderDetailId) o;
                return Objects.equals(orderNo, that.orderNo) && Objects.equals(itemNo, that.itemNo);
        }

        @Override
        public int hashCode() {
                return Objects.hash(orderNo, itemNo);
        }
}
