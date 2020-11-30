package com.FireSale.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDTO {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;
}
