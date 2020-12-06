package com.FireSale.api.service;

import com.FireSale.api.exception.ResourceNotFoundException;
import com.FireSale.api.mapper.AuctionMapper;
import com.FireSale.api.model.Auction;
import com.FireSale.api.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionMapper auctionMapper;

    @Transactional(readOnly = false)
    public Auction create(Auction auction) {
        auctionRepository.save(auction);
        return auction;
    }

    public Collection<Auction> getAuctions() {
        List<Auction> results = auctionRepository.findAll();
        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No auctions exists", Auction.class);
        }
        return results;
    }

    public Auction getAuctionById(final long id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No auction exists for id: %d", id), Auction.class));
    }

    @Transactional(readOnly = false)
    public Auction updateAuction(Long id, Auction auction) {
        final Auction existing = getAuctionById(id);
        existing.setName(auction.getName());
        existing.setDescription(auction.getDescription());
        existing.setStartDate(auction.getStartDate());
        existing.setEndDate(auction.getEndDate());
        existing.setMinimalBid(auction.getMinimalBid());
        existing.setIsFeatured(auction.getIsFeatured());
        existing.setStatus(auction.getStatus());
        existing.setIsDeleted(auction.getIsDeleted());
        return auctionRepository.save(auction);
    }

    @Transactional(readOnly = false)
    public Auction deleteAuction(Long id) {
        final Auction existing = getAuctionById(id);
        existing.setIsDeleted(true);
        return auctionRepository.save(existing);
    }
}













