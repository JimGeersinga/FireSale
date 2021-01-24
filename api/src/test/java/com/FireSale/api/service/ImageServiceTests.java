package com.FireSale.api.service;

import com.FireSale.api.repository.AuctionRepository;
import com.FireSale.api.repository.ImageRepository;
import com.FireSale.api.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTests {
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuctionRepository auctionRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private ImageService imageService;
}
