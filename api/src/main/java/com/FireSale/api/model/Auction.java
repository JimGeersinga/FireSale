package com.FireSale.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "auction")
    private Collection<Bid> bids;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private Collection<Image> images;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "auction_categories",
            joinColumns = { @JoinColumn(name = "auction_id") },
            inverseJoinColumns = { @JoinColumn(name = "categories_id") }
    )
    private Collection<Category> categories;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "auction_tags",
            joinColumns = { @JoinColumn(name = "auction_id") },
            inverseJoinColumns = { @JoinColumn(name = "tags_id") }
    )
    private Collection<Tag> tags;
}

