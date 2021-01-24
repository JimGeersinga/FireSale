package com.FireSale.api.service;

import com.FireSale.api.repository.BidRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BidServiceTests {

    @Mock
    private BidRepository bidRepository;
    @InjectMocks
    private BidService bidService;

}
