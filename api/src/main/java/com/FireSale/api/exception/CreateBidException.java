package com.FireSale.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreateBidException extends RuntimeException{
    public CreateBidException(String message) {
        super(message);
    }
}
