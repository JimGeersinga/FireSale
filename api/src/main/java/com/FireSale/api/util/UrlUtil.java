package com.FireSale.api.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UrlUtil {

    public static String getBaseUrl() {
        var uri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return uri.endsWith("/") ? uri : uri + "/";
    }
}
