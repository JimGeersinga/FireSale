package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Claim extends BaseEntity implements GrantedAuthority {
    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
