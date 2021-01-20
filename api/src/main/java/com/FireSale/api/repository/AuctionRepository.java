package com.FireSale.api.repository;

import com.FireSale.api.model.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

        @Query(value = "SELECT a FROM Auction a LEFT JOIN FETCH a.bids b WHERE a.status = 'READY' AND a.endDate > current_timestamp AND a.user.id = :id AND a.isDeleted = false order by a.endDate asc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'READY' AND a.endDate > current_timestamp AND a.user.id = :id AND a.isDeleted = false")
        List<Auction> findActiveAuctionsByUserId(@Param("id") long userId);

        @Query(value = "SELECT a FROM Auction a LEFT JOIN FETCH a.bids b WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp  order by a.endDate asc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'READY' AND a.startDate <= current_timestamp AND a.endDate > current_timestamp")
        Page<Auction> findActiveAuctions(Pageable pageable);

        @Query(value = "SELECT a FROM Auction a LEFT JOIN FETCH a.bids b WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND a.isFeatured = false order by a.endDate asc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND a.isFeatured = false")
        Page<Auction> findActiveAuctionsByIsFeaturedFalse(Pageable pageable);

        @Query(value = "SELECT a FROM Auction a LEFT JOIN FETCH a.bids b WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND a.isFeatured = true  order by a.endDate asc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND a.isFeatured = true")
        List<Auction> findActiveAuctionsByIsFeaturedTrue();

        @Query(value = "SELECT distinct a FROM Auction a LEFT JOIN FETCH a.bids b join a.categories c WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND c.id in :categories order by a.endDate asc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND c.id in :categories")
        List<Auction> findAuctionsByCategories(long[] categories);

        @Query(value = "SELECT distinct a FROM Auction a LEFT JOIN FETCH a.bids b join a.tags t WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND t.name in :tags order by a.endDate asc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND t.name in :tags")
        List<Auction> findAuctionsByTags(String[] tags);


        @Query(value = "SELECT distinct a FROM Auction a LEFT JOIN FETCH a.bids b join FavouriteAuction fa on fa.auction.id = a.id WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND fa.user.id = :user order by a.endDate asc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND fa.user.id = :user")
        List<Auction> findAuctionsByFavourite(Long user);

        @Query(value = "SELECT distinct a FROM Auction a LEFT JOIN FETCH a.bids b WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND b.user.id = :user order by a.endDate asc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND b.user.id = :user")
        List<Auction> findActiveByUserBid(Long user);

        @Query(value = "SELECT distinct a FROM Auction a LEFT JOIN FETCH a.bids b WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND a.name LIKE CONCAT('%',:name,'%') order by a.endDate asc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'READY' AND a.isDeleted = false AND a.startDate <= current_timestamp AND a.endDate > current_timestamp AND a.name LIKE CONCAT('%',:name,'%')")
        List<Auction> findAuctionsByNameLike(String name);

        @Query(value = "SELECT distinct a FROM Auction a LEFT JOIN FETCH a.bids b join a.categories c WHERE a.status = 'READY' AND a.isDeleted = false AND "
                        + "a.startDate <= current_timestamp AND a.endDate > current_timestamp AND "
                        + "a.name LIKE CONCAT('%',:name,'%') AND c.id in :categories order by a.endDate asc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'READY' AND a.isDeleted = false AND "
                        + "a.startDate <= current_timestamp AND a.endDate > current_timestamp AND "
                        + "a.name LIKE CONCAT('%',:name,'%') AND c.id in :categories")
        List<Auction> findAuctionsByCategoriesLikeAndNameLike(long[] categories, String name);

        @Query(value = "SELECT distinct a FROM Auction a LEFT JOIN FETCH a.bids b join a.tags t WHERE a.status = 'READY' AND a.isDeleted = false AND "
                        + "a.startDate <= current_timestamp AND a.endDate > current_timestamp AND "
                        + "a.name LIKE CONCAT('%',:name,'%') AND t.name in :tags order by a.endDate asc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'READY' AND a.isDeleted = false AND "
                        + "a.startDate <= current_timestamp AND a.endDate > current_timestamp AND "
                        + "a.name LIKE CONCAT('%',:name,'%') AND t.name in :tags")
        List<Auction> findAuctionsByTagsLikeAndNameLike(String[] tags, String name);

        @Query(value = "SELECT distinct a FROM Auction a LEFT JOIN FETCH a.bids b join a.tags t join a.categories c WHERE a.status = 'READY' AND a.isDeleted = false AND "
                        + "a.startDate <= current_timestamp AND a.endDate > current_timestamp AND "
                        + "a.name LIKE CONCAT('%',:name,'%') AND t.name in :tags AND c.id in :categories order by a.endDate asc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'READY' AND a.isDeleted = false AND "
                        + "a.startDate <= current_timestamp AND a.endDate > current_timestamp AND "
                        + "a.name LIKE CONCAT('%',:name,'%') AND t.name in :tags AND c.id in :categories")
        List<Auction> findAuctionsByTagsLikeAndCategoriesANDNameLike(String[] tags, long[] categories, String name);

        @Query(value = "SELECT distinct a FROM Auction a LEFT JOIN FETCH a.bids b join a.tags t join a.categories c WHERE a.status = 'READY' AND a.isDeleted = false AND "
                        + "a.startDate <= current_timestamp AND a.endDate > current_timestamp AND "
                        + "t.name in :tags AND c.id in :categories order by a.endDate asc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'READY' AND a.isDeleted = false AND "
                        + "a.startDate <= current_timestamp AND a.endDate > current_timestamp AND "
                        + "t.name in :tags AND c.id in :categories")
        List<Auction> findAuctionsByTagsLikeAndCategoriesLike(String[] tags, long[] categories);

        List<Auction> findByUserIdAndIsDeletedFalseOrderByEndDateDesc(long userId);
        @Query(value = "SELECT distinct a FROM Auction a LEFT JOIN FETCH a.bids b join a.finalBid b WHERE a.status = 'CLOSED' AND a.isDeleted = false and b.user.id = :user order by a.endDate desc", countQuery = "SELECT COUNT(a) FROM Auction a WHERE a.status = 'CLOSED' AND a.isDeleted = false and b.user.id = :user")
        List<Auction> findWonByUserBid(Long user);
}
