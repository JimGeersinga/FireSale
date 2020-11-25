package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Review extends BaseEntity {
    @Column(length = 1, nullable = false)
    private Integer rating;

    @Column(nullable = true)
    private String message;

    @ManyToOne(optional = false)
    private User receiver;

    @ManyToOne(optional = false)
    private User reviewer;
}