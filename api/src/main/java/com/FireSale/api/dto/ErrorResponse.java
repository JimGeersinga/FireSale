package com.FireSale.api.dto;

import com.FireSale.api.model.ErrorTypes;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class ErrorResponse extends ApiResponse {
    private String errorCode;
    private String errorMessage;
    private List<String> errorDetails;

    public ErrorResponse(ErrorTypes error, String message) {
        this(error, message, null);
    }

    public ErrorResponse(ErrorTypes error, String message, List<String> details) {
        super(false, null);

        this.errorCode = error.getCode();
        this.errorMessage = message;
        this.errorDetails = details;
    }
}
