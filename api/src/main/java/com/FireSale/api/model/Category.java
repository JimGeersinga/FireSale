package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Entity
public class Category extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    private Boolean archived;

    @ManyToMany(mappedBy = "categories")
    private Collection<Auction> auctions;
}
