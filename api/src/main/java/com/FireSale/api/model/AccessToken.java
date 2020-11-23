package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class AccessToken extends BaseEntity {
    private String token;
    private String refreshToken;
    private LocalDateTime expiresAt;
}
