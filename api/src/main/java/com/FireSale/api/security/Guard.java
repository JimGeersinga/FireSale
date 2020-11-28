package com.FireSale.api.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Guard {

    public boolean checkAuthority(final Authentication authentication, final String authority) {
        return  ((UserPrincipal) authentication.getPrincipal())
                .getAuthorities()
                .stream()
                .anyMatch(c -> c.getAuthority() == authority);
    }
}
