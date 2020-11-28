package com.FireSale.api.util;

import com.FireSale.api.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static UserPrincipal getSecurityContextUser() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
