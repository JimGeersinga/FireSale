package com.FireSale.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class WebsocketAuctionMessage<T>{
    private ResponseType responseType;
    private T data;
    private long userId;
    private LocalDateTime messageTime;
}
