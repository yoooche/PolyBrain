package com.polybrain.dto;

import lombok.Data;
import com.polybrain.model.ItemOrderTO;

@Data
public class OrderDetailDTO {
    Integer itemSales;
    Integer itemPrice;
    String itemName;
    ItemOrderTO itemOrderVO;
}
