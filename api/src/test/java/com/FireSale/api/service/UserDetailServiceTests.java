package com.FireSale.api.service;

import com.FireSale.api.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailService userDetailService;
}
