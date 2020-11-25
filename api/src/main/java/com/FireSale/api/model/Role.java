package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Role extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Claim> claims;

}
