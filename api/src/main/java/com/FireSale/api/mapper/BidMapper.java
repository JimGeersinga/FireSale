package com.FireSale.api.mapper;

import com.FireSale.api.dto.bid.BidDTO;
import com.FireSale.api.dto.bid.CreateBidDTO;
import com.FireSale.api.model.Bid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BidMapper extends ModelToDTOMapper<Bid, BidDTO> {
    Bid toModel(BidDTO dto);

    Bid toModel(CreateBidDTO dto);

    @Override
    @Mapping(target = "userName", source = "user.displayName")
    @Mapping(target = "userId", source = "user.id")
    BidDTO toDTO(Bid model);
}