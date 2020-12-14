package com.FireSale.api.dto.user;

import com.FireSale.api.dto.BaseDTO;
import com.FireSale.api.dto.address.AddressDTO;
import com.FireSale.api.dto.auction.ImageDTO;
import com.FireSale.api.model.Gender;
import com.FireSale.api.model.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO extends BaseDTO {
    private String email;
    private String displayName;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Role role;
    private AddressDTO address;
    private AddressDTO shippingAddress;
    private String avatar;
}