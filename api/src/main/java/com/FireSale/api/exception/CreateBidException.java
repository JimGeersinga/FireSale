package com.FireSale.api.exception;

import com.FireSale.api.model.ErrorTypes;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
@Setter
public class CreateBidException extends RuntimeException {

    private final ErrorTypes errorType;

    public CreateBidException(String message, ErrorTypes errorType) {
        super(message);
        this.errorType = errorType;
    }
}
