package com.FireSale.api.model;

import com.FireSale.api.dto.TagDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Auction extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = true, columnDefinition = "TEXT")
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

    @OneToOne()
    @JoinColumn(name = "final_bid_id")
    private Bid finalBid;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Bid> bids;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Image> images;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;
}

