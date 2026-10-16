package com.polybrain.model;

import com.polybrain.model.MemTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
@Entity
@Table(name = "BID_ORDER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidOrderTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "BID_ORDER_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidOrderNo;

    @Column(name = "FINAL_PRICE")
    private Integer finalPrice;

    @Column(name = "BID_EVENT_NO")
    private Integer bidEventNo;

    @Column(name = "MEM_NO")
    private Integer memNo;

    @Column(name = "BID_ITEM_NO")
    private Integer bidItemNo;

    @OneToOne
    @JoinColumn(name = "BID_EVENT_NO", updatable = false, insertable = false)
    private BidEventTO bidEventVo;

    @ManyToOne
    @JoinColumn(name = "MEM_NO", updatable = false, insertable = false)
    private MemTO memVo;

    @OneToOne
    @JoinColumn(name = "BID_ITEM_NO", updatable = false, insertable = false)
    private BidItemTO bidItemVo;

    @OneToOne
    @JoinColumn(name = "BID_ORDER_NO", updatable = false, insertable = false)
    private BidOrderDetailTO bidOrderDetailVo;


}
