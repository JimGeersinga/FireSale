package com.FireSale.api.dto.user;

import com.FireSale.api.dto.auction.CreateImageDTO;
import com.FireSale.api.model.Gender;
import com.FireSale.api.model.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class PatchUserDTO {
    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password should have a minimum eight characters, at least one letter, one number and one special character")
    private String password;

    private String displayName;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Role role;
    private CreateImageDTO avatar;
}
