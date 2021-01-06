package com.FireSale.api.repository;

import com.FireSale.api.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query("SELECT a FROM Auction a WHERE a.status = 'Ready' AND a.endDate > current_timestamp AND a.user.id = :id")
    List<Auction> findActiveAuctionsByUserId(@Param("id")long userId);
    List<Auction> findByUserId(long userId);
}