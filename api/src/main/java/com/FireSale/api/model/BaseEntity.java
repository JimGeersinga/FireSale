package com.FireSale.api.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Version
    @Column(nullable = false)
    private Integer version;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(nullable = true)
    private LocalDateTime modified;
}
