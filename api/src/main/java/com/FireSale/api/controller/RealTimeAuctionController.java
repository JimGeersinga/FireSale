package com.FireSale.api.controller;


import com.FireSale.api.dto.ResponseType;
import com.FireSale.api.dto.WebsocketAuctionMessage;
import com.FireSale.api.dto.bid.BidDTO;
import com.FireSale.api.model.Bid;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@AllArgsConstructor
@EnableScheduling
@Controller
@CrossOrigin("http://localhost:4200")
public class RealTimeAuctionController {
    final private SimpMessagingTemplate template;

    public void sendBidNotification(@DestinationVariable("auctionId")long auctionId, BidDTO bid) {
        template.convertAndSend("/rt-auction/updates/"+ auctionId, new WebsocketAuctionMessage<>(ResponseType.BID_PLACED, bid,bid.getUserId(), bid.getCreated()));
    }
}
