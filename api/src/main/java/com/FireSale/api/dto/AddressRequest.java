package com.FireSale.api.dto;

import lombok.Data;

@Data
public class AddressRequest {
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;
}
