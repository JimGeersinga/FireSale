package com.FireSale.api.dto.bid;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BidDTO {
    private Double value;
    private String userName;
    private long userId;
    private LocalDateTime created;
}
