package com.FireSale.api.service;

import com.FireSale.api.exception.ResourceNotFoundException;
import com.FireSale.api.model.Auction;
import com.FireSale.api.model.AuctionStatus;
import com.FireSale.api.repository.AuctionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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
        verify(auctionRepository).findById(any(Long.class));
    }

    @Test
    @DisplayName("Test getActiveAuctions Success")
    void testFindActive() {
        // Setup mock repository
        Auction a = new Auction();
        a.setId(1L);
        a.setName("test");
        a.setBids(new ArrayList<>());
        a.setTags(new ArrayList<>());
        a.setEndDate(LocalDateTime.now().plusDays(1));
        a.setStartDate(LocalDateTime.now());
        a.setStatus(AuctionStatus.READY);
        a.setDescription("Description");
        List<Auction> auctions = (Arrays.asList(a));
        Page<Auction> pagedAuctions = new PageImpl(auctions);
        doReturn(pagedAuctions).when(auctionRepository).findActiveAuctions(PageRequest.of(0, 10));
        // Execute service call
        Collection<?> returnedAuction = auctionService.getActiveAuctions(PageRequest.of(0, 10));
        // Assert response
        Assertions.assertTrue(returnedAuction != null, "Auction was not found");
        Assertions.assertSame(returnedAuction.toArray()[0], a, "The auction returned was not the same as the mock");
        verify(auctionRepository).findActiveAuctions(any(Pageable.class));
    }

    @Test
    @DisplayName("Test findByUserId Success")
    void testFindByUserId() {
        // Setup mock repository
        Auction a = new Auction();
        a.setId(1L);
        a.setName("test");
        a.setBids(new ArrayList<>());
        a.setTags(new ArrayList<>());
        a.setEndDate(LocalDateTime.now().plusDays(1));
        a.setStartDate(LocalDateTime.now());
        a.setStatus(AuctionStatus.READY);
        a.setDescription("Description");
        List<Auction> auctions = (Arrays.asList(a));
        doReturn(auctions).when(auctionRepository).findByUserIdAndIsDeletedFalseOrderByEndDateDesc(1L);
        // Execute service call
        var returnedAuction = auctionService.getAuctionsByUserId(1l);
        // Assert response
        Assertions.assertTrue(returnedAuction != null, "Auction was not found");
        Assertions.assertSame(returnedAuction.toArray()[0], a, "The auction returned was not the same as the mock");
        verify(auctionRepository).findByUserIdAndIsDeletedFalseOrderByEndDateDesc(any(Long.class));
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {
        // Setup mock repository
        doReturn(Optional.empty()).when(auctionRepository).findById(1L);

        // Execute service call
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            auctionService.findAuctionById(1l);
        });

        // Assert response
        assertThat(exception.getMessage()).isEqualTo("Resource of type [Auction] was not found: [No auction exists for id: 1]");
        verify(auctionRepository).findById(any(Long.class));
    }
}
