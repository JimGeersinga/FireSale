package com.FireSale.api.service;

import com.FireSale.api.aspect.LogDuration;
import com.FireSale.api.dto.auction.AuctionFilterDTO;
import com.FireSale.api.dto.auction.CreateAuctionDTO;
import com.FireSale.api.exception.ResourceNotFoundException;
import com.FireSale.api.exception.UnAuthorizedException;
import com.FireSale.api.mapper.AuctionMapper;
import com.FireSale.api.model.*;
import com.FireSale.api.repository.AuctionRepository;
import com.FireSale.api.repository.CategoryRepository;
import com.FireSale.api.repository.FavouriteAuctionRepository;
import com.FireSale.api.repository.UserRepository;
import com.FireSale.api.security.Guard;
import com.FireSale.api.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final TagService tagService;
    private final CategoryRepository categoryRepository;
    private final AuctionMapper auctionMapper;
    private final FavouriteAuctionRepository favouriteAuctionRepository;

    @LogDuration
    @Transactional(readOnly = false)
    public Auction create(CreateAuctionDTO createAuctionDTO) {
        final User user = userRepository.getOne(SecurityUtil.getSecurityContextUser().getUser().getId());
        final Collection<Category> categories = categoryRepository.findByIdIn(createAuctionDTO.getCategories());
        final Auction auction = auctionMapper.toModel(createAuctionDTO);

        auction.setUser(user);
        auction.setIsDeleted(false);
        auction.setIsFeatured(false);
        auction.setStatus(AuctionStatus.READY);
        auction.setCategories(categories);

        // Nog niet bestaande tags wegschrijven naar de database
        List<Tag> tags = new ArrayList<>();
        createAuctionDTO.getTags().stream().forEach(tag -> {
            var t = tagService.getTagByName(tag.getName());
            if (t == null) {
                tags.add(tagService.createTag(tag.getName()));
            } else {
                tags.add(t);
            }
        });

        // Alle tags voor auction ophalen en setten bij de auction
        auction.setTags(tags);
        var t = auction.getTags();
        return auctionRepository.save(auction);
    }

    @LogDuration
    public Collection<Auction> getAuctions() {
        return auctionRepository.findAll();
    }

    @LogDuration
    public Collection<Auction> getActiveAuctions(Pageable pageable) {
        return auctionRepository.findActiveAuctions(pageable).getContent();
    }

    @LogDuration
    public Collection<Auction> getFeatured() {
        var featured = auctionRepository.findActiveAuctionsByIsFeaturedTrue();

        var count = featured.stream().count();
        if (count < 10) {
            var get = 10 - (int) count;
            var remainder = PageRequest.of(0, get);
            var r = auctionRepository.findActiveAuctionsByIsFeaturedFalse(remainder);
            featured.addAll(r.getContent());
        }
        return featured;
    }

    @LogDuration
    public Collection<Auction> filterAuctions(AuctionFilterDTO dto) {
        if (dto.getCategories() != null && dto.getCategories().length > 0 && dto.getTags() != null && dto.getTags().length > 0 && dto.getName() != null ) {
            return auctionRepository.findAuctionsByTagsLikeAndCategoriesANDNameLike(dto.getTags(), dto.getCategories(), dto.getName());
        } else if (dto.getCategories() != null && dto.getCategories().length > 0 && dto.getTags() != null && dto.getTags().length > 0 ) {
            return auctionRepository.findAuctionsByTagsLikeAndCategoriesLike(dto.getTags(), dto.getCategories());
        } else if (dto.getCategories() != null && dto.getCategories().length > 0 && dto.getName() != null) {
            return auctionRepository.findAuctionsByCategoriesLikeAndNameLike(dto.getCategories(), dto.getName());
        } else if (dto.getTags() != null && dto.getTags().length > 0 && dto.getName() != null) {
            return auctionRepository.findAuctionsByTagsLikeAndNameLike(dto.getTags(), dto.getName());
        } else if (dto.getCategories() != null && dto.getCategories().length > 0) {
            return auctionRepository.findAuctionsByCategories(dto.getCategories());
        } else if (dto.getTags() != null && dto.getTags().length > 0) {
            return auctionRepository.findAuctionsByTags(dto.getTags());
        } else if (dto.getName() != null) {
            return auctionRepository.findAuctionsByNameLike(dto.getName());
        } else {
            return auctionRepository.findActiveAuctions(PageRequest.of(0, 20)).getContent();
        }
    }

    @LogDuration
    public Auction findAuctionById(final long id) {
        return auctionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("No auction exists for id: %d", id), ErrorTypes.AUCTION_NOT_FOUND));
    }

    @LogDuration
    public Collection<Auction> getAuctionsByUserId(final long userId) {
        return auctionRepository.findByUserIdAndIsDeletedFalseOrderByEndDateDesc(userId);
    }

    @LogDuration
    @Transactional(readOnly = false)
    public Auction updateAuction(Long id, CreateAuctionDTO createAuctionDTO) {
        final Auction existing = findAuctionById(id);
        final Collection<Category> categories = categoryRepository.findByIdIn(createAuctionDTO.getCategories());

        // if (Guard.isSelf(existing.getUser().getId())) {
        // throw new UnAuthorizedException("Cannot update the auction");
        // }

        if (existing.getStartDate().isBefore(LocalDateTime.now()))
            throw new UnAuthorizedException("Cannot change a running auction");
        existing.setName(createAuctionDTO.getName());
        existing.setDescription(createAuctionDTO.getDescription());
        existing.setStartDate(createAuctionDTO.getStartDate());
        existing.setEndDate(createAuctionDTO.getEndDate());
        existing.setMinimalBid(createAuctionDTO.getMinimalBid());
        existing.setCategories(categories);
        // existing.setTags(createAuctionDTO.getTags());
        // Nog niet bestaande tags wegschrijven naar de database
        List<Tag> tags = new ArrayList<>();
        createAuctionDTO.getTags().stream().forEach(tag -> {
            var t = tagService.getTagByName(tag.getName());
            if (t == null) {
                tags.add(tagService.createTag(tag.getName()));
            } else {
                tags.add(t);
            }
        });

        // Alle tags voor auction ophalen en setten bij de auction
        existing.setTags(tags);
        return auctionRepository.save(existing);
    }

    @LogDuration
    @Transactional(readOnly = false)
    public Auction patchAuction(Long id, Auction auction) {
        final Auction existing = findAuctionById(id);
        if (!Guard.isSelf(existing.getUser().getId()) && !Guard.isAdmin()) {
            throw new UnAuthorizedException("You are not allowed to cancel this auction");
        }
        if (auction.getName() != null)
            existing.setName(auction.getName());
        if (auction.getDescription() != null)
            existing.setDescription(auction.getDescription());
        if (auction.getStartDate() != null)
            existing.setStartDate(auction.getStartDate());
        if (auction.getEndDate() != null)
            existing.setEndDate(auction.getEndDate());
        if (auction.getMinimalBid() != null)
            existing.setMinimalBid(auction.getMinimalBid());
        if (auction.getIsFeatured() != null)
            existing.setIsFeatured(auction.getIsFeatured());
        if (auction.getStatus() != null)
            existing.setStatus(auction.getStatus());
        if (auction.getIsDeleted() != null)
            existing.setIsDeleted(auction.getIsDeleted());
        return auctionRepository.save(existing);
    }

    @LogDuration
    @Transactional(readOnly = false)
    public Auction deleteAuction(Long id) {
        final Auction existing = findAuctionById(id);

        if (existing.getStatus() != AuctionStatus.CANCELLED || !(Guard.isSelf(existing.getUser().getId()) || Guard.isAdmin())) {
            throw new UnAuthorizedException("Cannot delete the auction");
        }
        existing.setStatus(AuctionStatus.CLOSED);
        existing.setIsDeleted(true);
        return auctionRepository.save(existing);
    }

    @LogDuration
    @Transactional(readOnly = false)
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @LogDuration
    @Transactional(readOnly = true)
    public Collection<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void toggleFavourite(long auction, Long user, boolean favourite) {
        favouriteAuctionRepository.findByAuctionIdAndUserId(auction, user).ifPresentOrElse(fa -> {
            if(!favourite){
                favouriteAuctionRepository.delete(fa);
            }

        }, ()->{
            if(favourite)
            {
                var fav = new FavouriteAuction();
                fav.setUser(userRepository.getOne(user));
                fav.setAuction(auctionRepository.getOne(auction));
                favouriteAuctionRepository.save(fav);
            }
        });
    }

    @LogDuration
    @Transactional(readOnly = true)
    public Collection<Auction> getFavourites(Long id) {
        return auctionRepository.findAuctionsByFavourite(id);
    }

    @LogDuration
    @Transactional(readOnly = true)
    public Collection<Auction> getByUserBid(Long id) {
        return auctionRepository.findActiveByUserBid(id);
    }

    public Collection<Auction> getWonAuction(Long id) {
        return auctionRepository.findWonByUserBid(id);
    }
}
