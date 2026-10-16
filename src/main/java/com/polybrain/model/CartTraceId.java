package com.polybrain.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/** CartTraceTO 的複合主鍵（會員編號 + 商品編號）；Hibernate 用 equals、hashCode 判斷是不是同一筆 */
@Getter
@Setter
public class CartTraceId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer memNo;
    private Integer itemNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartTraceId that = (CartTraceId) o;
        return Objects.equals(memNo, that.memNo) && Objects.equals(itemNo, that.itemNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memNo, itemNo);
    }
}
