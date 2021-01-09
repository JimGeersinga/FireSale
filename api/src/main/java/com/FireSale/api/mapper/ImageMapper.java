package com.FireSale.api.mapper;

import com.FireSale.api.dto.auction.ImageDTO;
import com.FireSale.api.model.Image;
import com.FireSale.api.util.UrlUtil;
import org.mapstruct.*;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper extends ModelToDTOMapper<Image, ImageDTO> {

    @Override
    @Mapping(target = "path", ignore = true)
    ImageDTO toDTO(Image image);

    @AfterMapping
    default void setPathWithBaseUrl(@MappingTarget ImageDTO imageDTO, Image image) {
        imageDTO.setPath(UrlUtil.getBaseUrl() + "file/image/" + image.getId());
    }
}