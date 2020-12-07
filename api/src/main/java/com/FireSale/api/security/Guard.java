package com.FireSale.api.security;

import com.FireSale.api.model.Role;
import com.FireSale.api.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Guard {
    public static boolean isAdmin() {
        return SecurityUtil.getSecurityContextUser().getUser().getRole() == Role.ADMIN;
    }

    public static boolean isSelf(final Long id) {
        return SecurityUtil.getSecurityContextUser().getUser().getId() == id;
    }
}
