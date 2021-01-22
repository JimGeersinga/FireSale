package com.FireSale.api.dto.auction;

import com.FireSale.api.dto.user.PersonInfoDTO;
import lombok.Data;

@Data
public class AuctionWinningInfoDTO {
    private PersonInfoDTO owner;

    private PersonInfoDTO winner;
}
