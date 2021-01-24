package com.FireSale.api.security;

import com.FireSale.api.model.Role;
import com.FireSale.api.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class Guard {
    private Guard() {
//        throw new IllegalStateException("Utility class");
    }

    public static boolean isAdmin() {
        return Objects.requireNonNull(SecurityUtil.getSecurityContextUser()).getUser().getRole() == Role.ADMIN;
    }

    public static boolean isSelf(final Long id) {
        return Objects.requireNonNull(SecurityUtil.getSecurityContextUser()).getUser().getId().equals(id);
    }
}
