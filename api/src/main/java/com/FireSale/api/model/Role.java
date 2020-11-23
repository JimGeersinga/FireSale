package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity
public class Role extends BaseEntity {
    private String name;
    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private Set<Claim> claims;

}
