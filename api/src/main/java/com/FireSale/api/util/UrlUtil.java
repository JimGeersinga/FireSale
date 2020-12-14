package com.FireSale.api.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UrlUtil {

    public static String getBaseUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }
}
