package com.FireSale.api.service;

import com.FireSale.api.exception.ResourceNotFoundException;
import com.FireSale.api.mapper.AuctionMapper;
import com.FireSale.api.model.Auction;
import com.FireSale.api.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionMapper auctionMapper;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository, AuctionMapper auctionMapper) {
        this.auctionRepository = auctionRepository;
        this.auctionMapper = auctionMapper;
    }

    // C - post (create)
    @Transactional(readOnly = false)
    public Auction create(Auction auction) {
        auctionRepository.save(auction);
        return auction;
    }

    // R 1 - get all
    public Collection<Auction> getAuctions() {
        List<Auction> results = auctionRepository.findAll();
        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No auctions exists", Auction.class);
        }
        return results;
    }

    // R 2 - get by id
    public Auction getAuctionById(final long id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No auction exists for id: %d", id), Auction.class));
    }

    // U - put by id (update)
    public Auction updateAuction(Long id, Auction auction) {
        auction.setId((id));
        return auctionRepository.save(auction);
    }

    // D - delete by id
    public void deleteAuction(Long id) {
        auctionRepository.deleteById(id);
    }
}













