package com.FireSale.api.dto.usersecurity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Getter
@Setter
public class ChangepasswordDTO {
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password should have a minimum eight characters, at least one letter, one number and one special character")
    @NotEmpty
    private String password;
    @NotEmpty
    private String token;
}
