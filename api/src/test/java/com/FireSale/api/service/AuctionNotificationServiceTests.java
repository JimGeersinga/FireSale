package com.FireSale.api.service;

import com.FireSale.api.repository.AuctionRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@ExtendWith(MockitoExtension.class)
public class AuctionNotificationServiceTests {
    @Mock
    private SimpMessagingTemplate template;
    @Mock
    private AuctionRepository auctionRepository;
    @InjectMocks
    private AuctionNotificationService auctionNotificationService;

}
