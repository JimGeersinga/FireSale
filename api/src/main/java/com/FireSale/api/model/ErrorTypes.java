package com.FireSale.api.model;

import lombok.Getter;

@Getter
public enum ErrorTypes {
    LOGIN_FAILED("LOGIN_FAILED"),
    ACCOUNT_IS_LOCKED("ACCOUNT_DISABLED"),
    UNKOWN("UNKOWN"),
    AUCTION_CREATION_FAILED("AUCTION_CREATION_FAILED");

    private String code;

    ErrorTypes(String code) {
        this.code = code;
    }
}
