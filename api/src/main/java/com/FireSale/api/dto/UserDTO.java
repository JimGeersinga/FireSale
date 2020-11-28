package com.FireSale.api.dto;

import com.FireSale.api.model.Gender;
import com.FireSale.api.model.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UserDTO {
    private String email;
    private String nickname;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Role role;
    private Set<String> claims;
}
