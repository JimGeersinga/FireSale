package com.FireSale.api.dto.auction;

import com.FireSale.api.dto.BaseDTO;
import com.FireSale.api.dto.bid.BidDTO;
import com.FireSale.api.dto.category.CategoryDTO;
import com.FireSale.api.dto.user.UserProfileDTO;
import com.FireSale.api.model.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class AuctionDTO extends BaseDTO {
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    private Double minimalBid;
    private Boolean isFeatured;
    private AuctionStatus status;
    private BidDTO finalBid;
    private UserProfileDTO user;
    private Set<BidDTO> bids;
    private Set<ImageDTO> images;
    private Set<CategoryDTO> categories;
    private Set<Tag> tags;

}
