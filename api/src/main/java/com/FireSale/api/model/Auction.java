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
    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = true)
    private Double minimalBid;

    @Column(nullable = false)
    private Boolean isFeatured = false;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuctionStatus status;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "final_bid_id")
    private Bid finalBid;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Bid> bids;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Image> images;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Category> categories;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Tag> tags;
}

