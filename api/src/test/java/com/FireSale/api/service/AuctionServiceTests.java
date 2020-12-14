package com.FireSale.api.service;

import com.FireSale.api.model.Auction;
import com.FireSale.api.repository.AuctionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class AuctionServiceTests {
    @Autowired
    private AuctionService auctionService;

    @MockBean
    private AuctionRepository auctionRepository;

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        // Setup mock repository
        Auction auction = new Auction();
        doReturn(Optional.of(auction)).when(auctionRepository).findById(1L);

        // Execute service call
        Optional<Auction> returnedAuction = Optional.ofNullable(auctionService.findAuctionById(1));

        // Assert response
        Assertions.assertTrue(returnedAuction.isPresent(), "Auction was not found");
        Assertions.assertSame(returnedAuction.get(), auction, "The auction returned was not the same as the mock");
    }

    // @Test
    // @DisplayName("Test findById Not Found")
    // void testFindByIdNotFound() {
    // // Setup mock repository
    // doReturn(Optional.empty()).when(auctionRepository).findById(1L);
    //
    // // Execute service call
    // Optional<Auction> returnedAuction =
    // Optional.ofNullable(auctionService.getAuctionById(1L));
    //
    // // Assert response
    // Assertions.assertFalse(returnedAuction.isPresent(), "Auction should not be
    // found");
    // }
}
