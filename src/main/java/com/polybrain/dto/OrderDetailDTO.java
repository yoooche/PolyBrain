package com.polybrain.dto;

import lombok.Getter;
import lombok.Setter;
import com.polybrain.model.ItemOrderTO;

@Getter
@Setter
public class OrderDetailDTO {
    private Integer itemSales;
    private Integer itemPrice;
    private String itemName;
    private ItemOrderTO itemOrderVO;
}
