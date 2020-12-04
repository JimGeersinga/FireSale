package com.FireSale.api.mapper;

import com.FireSale.api.dto.auction.AuctionDTO;
import com.FireSale.api.model.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuctionMapper extends ModelToDTOMapper<Auction, AuctionDTO> {
}