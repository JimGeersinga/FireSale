package com.FireSale.api.exception;

import com.FireSale.api.model.ErrorTypes;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
@Setter
public class InvalidResetTokenException extends RuntimeException {

    private ErrorTypes errorType;

    public InvalidResetTokenException(String message, ErrorTypes errorType) {
        super(String.format("Password reset token is invalid: [%s]", message));
        this.errorType = errorType;
    }
}
