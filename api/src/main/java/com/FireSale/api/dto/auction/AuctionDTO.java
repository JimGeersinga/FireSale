package com.FireSale.api.dto.auction;

import com.FireSale.api.dto.BaseDTO;
import com.FireSale.api.dto.category.CategoryDTO;
import com.FireSale.api.model.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class AuctionDTO extends BaseDTO {
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double minimalBid;
    private Boolean isFeatured;
    private AuctionStatus status;
    private Bid finalBid;
    private Set<Bid> bids;
    private Set<ImageDTO> images;
    private Set<CategoryDTO> categories;
    private Set<Tag> tags;

}
