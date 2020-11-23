package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Review extends BaseEntity {

    @Column(length = 1)
    private Integer rating;
    private String message;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User receiver;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User reviewer;