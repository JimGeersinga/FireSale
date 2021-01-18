package com.FireSale.api.service;

import com.FireSale.api.aspect.LogDuration;
import com.FireSale.api.exception.CreateBidException;
import com.FireSale.api.model.Bid;
import com.FireSale.api.repository.AuctionRepository;
import com.FireSale.api.repository.BidRepository;
import com.FireSale.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BidService {
    private final BidRepository bidRepository;

    @LogDuration
    @Transactional(readOnly = false)
    public Bid create(Bid bid) {
        bid.setCreated(LocalDateTime.now());

        if(bid.getCreated().compareTo(bid.getAuction().getEndDate()) > 0)
        {
            throw new CreateBidException("Auction_already_completed");
        }

        var bids = bidRepository.findByAuctionId(bid.getAuction().getId());
        bids.stream().forEach(b -> {
            if(b.getValue() >= bid.getValue())
            {
                throw new CreateBidException("Bid_to_low");
            }
        });

        return bidRepository.save(bid);
    }

    @LogDuration
    @Transactional(readOnly = true)
    public List<Bid> getForAuction(long auction) {
        return bidRepository.findByAuctionId(auction);
    }

    @LogDuration
    @Transactional(readOnly = true)
    public List<Bid> getForUser(long user) {
        return bidRepository.findByUserId(user);
    }
}
