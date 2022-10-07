package com.ssafy.dockchodogam.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long itemId;
    private String name;
    private Integer priceWon;
    private Integer priceCash;
    private Integer itemCnt;
    private boolean isDuplicate;
}
