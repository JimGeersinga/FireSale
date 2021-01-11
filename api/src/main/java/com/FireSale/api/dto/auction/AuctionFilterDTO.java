package com.FireSale.api.dto.auction;

import lombok.Data;

@Data
public class AuctionFilterDTO {
    private long[] categories;
    private long[] tags;
    private String name;
}
