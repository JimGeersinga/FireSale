package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseEntity {
    private String email;
    private String password;
    private String nickname;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Address address;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Address shippingAddress;

    @OneToOne( cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Role role;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "receiver")
    private Set<Review> incomingReviews;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "reviewer")
    private Set<Review> outgoingReviews;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<AccessToken> accessTokens;

    @OneToMany(mappedBy = "user")
    private Set<Bid> bids;
}