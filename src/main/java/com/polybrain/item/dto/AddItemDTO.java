package com.polybrain.item.dto;

import com.polybrain.item.entity.Item;
import lombok.Data;

import java.util.List;

@Data
public class AddItemDTO {
    List<String> itemImageList;
    Item item;
}
