package com.FireSale.api.dto.auction;

import com.FireSale.api.dto.BaseDTO;
import lombok.Data;

import java.util.Base64;

@Data
public class CreateImageDTO {
    private Long id;
    private byte[] path;
    private String type;
    private Integer sort;

}
