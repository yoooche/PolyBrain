package com.polybrain.order.dto;

import lombok.Data;
import com.polybrain.order.entity.ItemOrderVO;

@Data
public class OrderDetailDTO {
    Integer itemSales;
    Integer itemPrice;
    String itemName;
    ItemOrderVO itemOrderVO;
}
