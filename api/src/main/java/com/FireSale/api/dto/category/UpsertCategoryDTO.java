package com.FireSale.api.dto.category;

import lombok.Data;

@Data
public class UpsertCategoryDTO {

    private String name;
    private Boolean archived;
}
