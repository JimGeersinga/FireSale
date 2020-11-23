package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Bid extends BaseEntity {
    private Double value;
    private LocalDate timeStamp;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private User user;
}
