package com.FireSale.api.mapper;

import com.FireSale.api.dto.category.CategoryDTO;
import com.FireSale.api.dto.category.UpsertCategoryDTO;
import com.FireSale.api.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper extends ModelToDTOMapper<Category, CategoryDTO> {

    Category toModel(UpsertCategoryDTO upsertCategoryDTO);
}
