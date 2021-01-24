package com.FireSale.api.model;

import lombok.Getter;

@Getter
public enum ErrorTypes {
    LOGIN_FAILED("LOGIN_FAILED"),
    ACCOUNT_IS_LOCKED("ACCOUNT_DISABLED"),
    UNKNOWN("UNKNOWN"),
    VALIDATION_FAILED("VALIDATION_FAILED"),
    UNAUTHORIZED("UNAUTHORIZED"),
    NOT_FOUND("NOT_FOUND"),
    AUCTION_NOT_FOUND("AUCTION_NOT_FOUND"),
    ADDRESS_NOT_FOUND("ADDRESS_NOT_FOUND"),
    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND"),
    IMAGE_NOT_FOUND("IMAGE_NOT_FOUND"),
    TAG_NOT_FOUND("TAG_NOT_FOUND"),
    USER_NOT_FOUND("USER_NOT_FOUND"),
    INVALID_RESET_TOKEN("INVALID_RESET_TOKEN"),
    EXPIRED_RESET_TOKEN("EXPIRED_RESET_TOKEN"),
    BID_TOO_LOW("BID_TOO_LOW"),
    AUCTION_ALREADY_COMPLETED("AUCTION_ALREADY_COMPLETED");

    private final String code;

    ErrorTypes(String code) {
        this.code = code;
    }
}
