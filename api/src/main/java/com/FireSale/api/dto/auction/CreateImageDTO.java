package com.FireSale.api.dto.auction;

import com.FireSale.api.dto.BaseDTO;
import lombok.Data;

import java.util.Base64;

@Data
public class CreateImageDTO {

    private byte[] imageB64;
    private String type;
    private Integer sort;

}
