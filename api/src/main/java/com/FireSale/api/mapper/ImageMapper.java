package com.FireSale.api.mapper;

import com.FireSale.api.dto.auction.ImageDTO;
import com.FireSale.api.model.Image;
import com.FireSale.api.util.UrlUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper extends ModelToDTOMapper<Image, ImageDTO> {

    @Override
    @Mapping(target = "path", source = "path", qualifiedByName = "imageWithBaseUrl")
    ImageDTO toDTO(Image image);

    @Named("imageWithBaseUrl")
    public static String imageWithBaseUrl(String path) {
        return UrlUtil.getBaseUrl() + path;
    }
}