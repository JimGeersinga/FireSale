package com.FireSale.api.dto.user;

import com.FireSale.api.dto.BaseDTO;
import com.FireSale.api.dto.auction.ImageDTO;
import lombok.Data;

@Data
public class UserProfileDTO extends BaseDTO {
    private String displayName;
    private String avatar;
}
