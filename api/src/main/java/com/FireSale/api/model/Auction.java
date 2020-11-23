package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Auction extends BaseEntity {
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double minimalBid;
    private boolean isFeatured;
    private AuctionStatus status;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Bid finalBid;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Bid> bids;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Image> images;
}
