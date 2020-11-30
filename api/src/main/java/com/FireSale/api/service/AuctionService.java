package com.FireSale.api.service;

import com.FireSale.api.mapper.AuctionMapper;
import com.FireSale.api.model.Auction;
import com.FireSale.api.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

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

    // R 1 - findall
    public Collection<Auction> getAuctions() {
        return auctionRepository.findAll();
    }

    // R 2 - get by id
    public Optional<Auction> getAuctionById(Long id) {
        return auctionRepository.findById(id);
    }

    // U - put by id (update)
    public void updateAuction(Long id, Auction auction) {
        auction.setId((id));
        auctionRepository.save(auction);
    }

    // D - delete by id
    public void deleteAuction(Long id) {
        auctionRepository.deleteById(id);
    }
}













