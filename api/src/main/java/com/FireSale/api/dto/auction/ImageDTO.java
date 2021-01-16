package com.FireSale.api.dto.auction;

import com.FireSale.api.dto.BaseDTO;
import lombok.Data;

@Data
public class ImageDTO extends BaseDTO {
    private byte[] path;
    private String type;
    private Integer sort;
}
