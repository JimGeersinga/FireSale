package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
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
    private Date dateOfBirth;
    private Gender gender;
    @OneToOne(
        cascade = CascadeType.REMOVE,
        fetch = FetchType.LAZY
    )
    private Address address;
    @OneToOne(
        cascade = CascadeType.REMOVE,
        fetch = FetchType.LAZY
    )
    private Address shippingAddress;
    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "review"
    )
    private Set<Review> incomingReviews;
    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY.EAGER,
            mappedBy = "review"
    )
    private Set<Review> outgoingReviews;
}