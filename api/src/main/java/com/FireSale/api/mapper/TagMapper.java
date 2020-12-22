package com.FireSale.api.mapper;

import com.FireSale.api.dto.TagDTO;
import com.FireSale.api.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper extends ModelToDTOMapper<Tag, TagDTO> {
    Tag toModel(TagDTO tagDto);
}
