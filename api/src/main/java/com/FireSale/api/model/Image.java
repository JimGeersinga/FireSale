package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Image extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String path;

    @Column(nullable = false)
    private Integer sort = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "auction_id")
    private Auction auction;
}
