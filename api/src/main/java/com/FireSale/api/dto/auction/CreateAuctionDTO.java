package com.FireSale.api.dto.auction;

import com.FireSale.api.dto.BaseDTO;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateAuctionDTO extends BaseDTO {
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double minimalBid;
    private List<CreateImageDTO> images;
    private List<Long> categories;
}
