package com.polybrain.dto;

import com.polybrain.model.BidItemTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidItemListDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer bidEventNo;
    private Integer floorPrice;
    private Integer directivePrice;
    private Timestamp startTime;
    private Timestamp closeTime;
    private BidItemTO bidItemVo;
    private List<String> bidItemPic;
}
