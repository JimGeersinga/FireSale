package com.FireSale.api.dto.user;

import com.FireSale.api.dto.address.UpdateAddressDTO;
import com.FireSale.api.model.Gender;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class RegisterDTO {
    @NotEmpty(message = "Email must have a value")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password must have a value")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password should have a minimum eight characters, at least one letter, one number and one special character")
    private String password;

    @NotEmpty(message = "Display name must have a value")
    private String displayName;

    @NotEmpty(message = "First name must have a value")
    private String firstName;

    @NotEmpty(message = "Last name must have a value")
    private String lastName;

    @NotNull(message = "Date of birth must have a value")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender must have a value")
    private Gender gender;

    @NotNull(message = "Address must have a value")
    private UpdateAddressDTO address;

    private UpdateAddressDTO shippingAddress;
}
