package com.polybrain.dto;

import com.polybrain.model.Item;
import lombok.Data;

import java.util.List;

@Data
public class AddItemDTO {
    List<String> itemImageList;
    Item item;
}
