package com.firesale.api.security;

import com.firesale.api.model.Role;
import com.firesale.api.util.SecurityUtil;
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
