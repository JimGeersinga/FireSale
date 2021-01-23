package com.FireSale.api.service;


import com.FireSale.api.dto.ResponseType;
import com.FireSale.api.dto.WebsocketAuctionMessage;
import com.FireSale.api.dto.bid.BidDTO;
import com.FireSale.api.model.Auction;
import com.FireSale.api.model.AuctionStatus;
import com.FireSale.api.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;

@RequiredArgsConstructor
@EnableScheduling
@Service
public class AuctionNotificationService {
    final private SimpMessagingTemplate template;
    final private AuctionRepository auctionRepository;

    public void sendBidNotification(@DestinationVariable("auctionId") long auctionId, BidDTO bid) {
        template.convertAndSend("/rt-auction/updates/" + auctionId, new WebsocketAuctionMessage<>(ResponseType.BID_PLACED, bid, bid.getUserId(), bid.getCreated()));
    }

    public void sendStatusNotification(@DestinationVariable("auctionId") long auctionId, AuctionStatus auctionStatus) {
        template.convertAndSend("/rt-auction/updates/" + auctionId, new WebsocketAuctionMessage<>(ResponseType.UPDATED, auctionStatus, null, LocalDateTime.now()));
    }

    @Scheduled(fixedDelay = 1000)
    @Transactional(readOnly = false)
    public void closeAuction() {
        var auctions = auctionRepository.getFinalizedAuctions();
        for (Auction auction : auctions) {
            if (auction.getBids().size() > 0) {
                var finalBid = Collections.max(auction.getBids(), Comparator.comparing(b -> b.getValue()));
                auction.setFinalBid(finalBid);
            }

            auction.setStatus(AuctionStatus.CLOSED);

            auctionRepository.save(auction);

            sendStatusNotification(auction.getId(), auction.getStatus());
        }
    }
}
