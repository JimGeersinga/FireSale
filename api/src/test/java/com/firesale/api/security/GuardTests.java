package com.firesale.api.security;

import com.firesale.api.model.Role;
import com.firesale.api.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuardTests {

    @Test
    @DisplayName("Test Guard is admin with no authentication Success")
    void isAdminNoAuthetication() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Execute service call
        var isAdmin = Guard.isAdmin();

        // Assert response
        Assertions.assertSame(false, isAdmin, "No authentication");
    }

    @Test
    @DisplayName("Test Guard is admin with authentication, wrong role Success")
    void isAdminWithAutheticationWrongRole() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        UserPrincipal principal = mock(UserPrincipal.class);
        User user = mock(User.class);
        when(user.getRole()).thenReturn(Role.USER);
        when(principal.getUser()).thenReturn(user);
        when((authentication.getPrincipal())).thenReturn(principal);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Execute service call
        var isAdmin = Guard.isAdmin();

        // Assert response
        Assertions.assertSame(false, isAdmin, "Incorrect role");
    }

    @Test
    @DisplayName("Test Guard is admin with authentication, correct role Success")
    void isAdminWithAutheticationCorrectRole() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        UserPrincipal principal = mock(UserPrincipal.class);
        User user = mock(User.class);
        when(user.getRole()).thenReturn(Role.ADMIN);
        when(principal.getUser()).thenReturn(user);
        when((authentication.getPrincipal())).thenReturn(principal);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Execute service call
        var isAdmin = Guard.isAdmin();

        // Assert response
        Assertions.assertSame(true, isAdmin, "Incorrect role");
    }

    @Test
    @DisplayName("Test Guard is admin with no authentication Success")
    void isSelfNoAuthetication() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Execute service call
        var isSelf = Guard.isSelf(1L);

        // Assert response
        Assertions.assertSame(false, isSelf, "No authentication");
    }

    @Test
    @DisplayName("Test Guard is self with authentication, wrong id Success")
    void isSelfWithAutheticationWrongId() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        UserPrincipal principal = mock(UserPrincipal.class);
        User user = mock(User.class);
        when(user.getId()).thenReturn(2L);
        when(principal.getUser()).thenReturn(user);
        when((authentication.getPrincipal())).thenReturn(principal);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Execute service call
        var isSelf = Guard.isSelf(1L);

        // Assert response
        Assertions.assertSame(false, isSelf, "Incorrect id");
    }

    @Test
    @DisplayName("Test Guard is self with authentication, correct id Success")
    void isSelfWithAutheticationCorrectId() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        UserPrincipal principal = mock(UserPrincipal.class);
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        when(principal.getUser()).thenReturn(user);
        when((authentication.getPrincipal())).thenReturn(principal);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Execute service call
        var isSelf = Guard.isSelf(1L);

        // Assert response
        Assertions.assertSame(true, isSelf, "Incorrect id");
    }
}
