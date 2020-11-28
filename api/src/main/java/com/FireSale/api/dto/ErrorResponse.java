package com.FireSale.api.dto;

import com.FireSale.api.model.ErrorTypes;
import lombok.Getter;

@Getter
public class ErrorResponse extends ApiResponse {
    private String errorCode;
    private String errorMessage;

    public ErrorResponse(ErrorTypes error, String message) {
        super(false, null);

        this.errorCode = error.getCode();
        this.errorMessage = message;
    }
}
