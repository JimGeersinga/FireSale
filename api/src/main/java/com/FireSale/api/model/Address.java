package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Address extends BaseEntity {
    private String street;
    private Integer houseNumber;
    private String postalCode;
    private String city;
    private String country;
}
