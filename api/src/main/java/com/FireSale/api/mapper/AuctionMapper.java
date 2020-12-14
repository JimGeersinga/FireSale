package com.FireSale.api.mapper;

import com.FireSale.api.dto.auction.AuctionDTO;
import com.FireSale.api.dto.auction.CreateAuctionDTO;
import com.FireSale.api.dto.user.PatchUserDTO;
import com.FireSale.api.dto.user.RegisterDTO;
import com.FireSale.api.dto.user.UpdateUserDTO;
import com.FireSale.api.dto.user.UserProfileDTO;
import com.FireSale.api.model.Auction;
import com.FireSale.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuctionMapper extends ModelToDTOMapper<Auction, AuctionDTO> {

    @Mapping(target = "images", ignore = true)
    @Mapping(target="categories", ignore = true)
    Auction toModel(CreateAuctionDTO createAuctionDTO);

}