package com.FireSale.api.service;

import com.FireSale.api.aspect.LogDuration;
import com.FireSale.api.model.AuctionStatus;
import com.FireSale.api.model.Bid;
import com.FireSale.api.model.ErrorTypes;
import com.FireSale.api.exception.CreateBidException;
import com.FireSale.api.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

        if (!bid.getAuction().getStatus().equals(AuctionStatus.READY)) {
            throw new CreateBidException("Auction already completed", ErrorTypes.AUCTION_ALREADY_COMPLETED);
        }

        var bids = bidRepository.findByAuctionId(bid.getAuction().getId());
        bids.stream().forEach(b -> {
            if (b.getValue() >= bid.getValue()) {
                throw new CreateBidException("Bid to low", ErrorTypes.BID_TOO_LOW);
            }
        });
        return bidRepository.save(bid);
    }

    @LogDuration
    @Transactional(readOnly = true)
    public List<Bid> getForAuction(long auction) {
        return bidRepository.findByAuctionId(auction);
    }
}
