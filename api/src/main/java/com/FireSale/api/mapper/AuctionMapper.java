package com.FireSale.api.mapper;

import com.FireSale.api.dto.auction.AuctionDTO;
import com.FireSale.api.model.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuctionMapper extends ModelToDTOMapper<Auction, AuctionDTO> {
    Auction DTOToEntity(AuctionDTO auctionDTO);

    AuctionDTO EntityToDTO(Auction auction);
}