package com.FireSale.api.dto;

import com.FireSale.api.model.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String displayName;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private AddressRequest address;
}
