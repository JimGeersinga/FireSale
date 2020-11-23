package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Claim extends BaseEntity {
    private String name;
}
