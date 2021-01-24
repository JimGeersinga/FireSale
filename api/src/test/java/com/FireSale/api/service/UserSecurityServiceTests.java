package com.FireSale.api.service;

import com.FireSale.api.repository.PasswordResetTokenRepository;
import com.FireSale.api.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserSecurityServiceTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @InjectMocks
    private UserSecurityService userSecurityService;
}
