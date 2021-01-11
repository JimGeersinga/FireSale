package com.FireSale.api.dto.auction;

import lombok.Data;

@Data
public class AuctionFilterDTO {
    private long[] categories = new long[0];
    private long[] tags = new long[0];
    private String name;
}
