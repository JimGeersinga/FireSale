package com.FireSale.api.mapper;

import com.FireSale.api.dto.auction.ImageDTO;
import com.FireSale.api.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper extends ModelToDTOMapper<Image, ImageDTO> {
}