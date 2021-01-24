package com.FireSale.api.util;

import com.FireSale.api.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    private SecurityUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static UserPrincipal getSecurityContextUser() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal == "anonymousUser") return  null;
        return (UserPrincipal) principal;
    }
}
