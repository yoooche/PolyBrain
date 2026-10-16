package com.polybrain.dto;

import com.polybrain.model.BidItemTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidItemDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private BidItemTO bidItemVo;
    private List<String> bidItemPic;
}
