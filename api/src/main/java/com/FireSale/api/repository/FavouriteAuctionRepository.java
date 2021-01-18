package com.FireSale.api.repository;

import com.FireSale.api.model.FavouriteAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FavouriteAuctionRepository extends JpaRepository<FavouriteAuction, Long> {
    @Query("select fa from FavouriteAuction fa inner join fa.auction a inner join fa.user u where a.id = :auction and u.id = :user")
    public Optional<FavouriteAuction> findByAuctionIdAndUserId(Long auction, Long user);

}
