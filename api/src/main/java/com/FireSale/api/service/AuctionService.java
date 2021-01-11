package com.FireSale.api.service;

import com.FireSale.api.dto.TagDTO;
import com.FireSale.api.dto.auction.AuctionFilterDTO;
import com.FireSale.api.dto.auction.CreateAuctionDTO;
import com.FireSale.api.exception.ResourceNotFoundException;
import com.FireSale.api.exception.UnAuthorizedException;
import com.FireSale.api.mapper.AuctionMapper;
import com.FireSale.api.mapper.TagMapper;
import com.FireSale.api.model.*;
import com.FireSale.api.repository.AuctionRepository;
import com.FireSale.api.repository.CategoryRepository;
import com.FireSale.api.repository.UserRepository;
import com.FireSale.api.security.Guard;
import com.FireSale.api.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.mockito.internal.util.collections.Sets;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final TagService tagService;

    private final CategoryRepository categoryRepository;
    private final AuctionMapper auctionMapper;

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

        //Nog niet bestaande tags wegschrijven naar de database
        List<Tag> tags = new ArrayList<>();
        createAuctionDTO.getTags().stream().forEach(tag -> {
            var t = tagService.getTagByName(tag.getName());
            if(t == null) {
                tags.add(tagService.createTag(tag.getName()));
            }else{
                tags.add(t);
            }
        });

        //Alle tags voor auction ophalen en setten bij de auction
        auction.setTags( tags);
        var t = auction.getTags();
        return auctionRepository.save(auction);
    }

    public Collection<Auction> getAuctions() {
        return auctionRepository.findAll();
    }

    public Collection<Auction> getActiveAuctions(Pageable pageable) {
        return auctionRepository.findActiveAuctions(pageable).getContent();
    }



    public Collection<Auction> getFeatured() {
        var featured = auctionRepository.findActiveAuctionsByIsFeaturedTrue();

        var count = featured.stream().count();
        if( count < 10)
        {
            var get = 10 - (int)count;
            var remainder = PageRequest.of(0, get);
            var r = auctionRepository.findActiveAuctionsByIsFeaturedFalse(remainder);
            featured.addAll(r.getContent());
        }
        return featured;
    }

    public Collection<Auction> filterAuctions(AuctionFilterDTO dto) {
        if(dto.getCategories() != null && dto.getName() != null && dto.getTags() != null) return auctionRepository.findAuctionsByTagsLikeAndCategoriesANDNameLike(dto.getTags(), dto.getCategories(), dto.getName());
        if(dto.getCategories() != null && dto.getName() != null) return auctionRepository.findAuctionsByCategoriesLikeAndNameLike(dto.getCategories(), dto.getName());
        if(dto.getCategories() != null && dto.getTags() != null) return auctionRepository.findAuctionsByTagsLikeAndCategoriesLike(dto.getCategories(), dto.getTags());
        if(dto.getTags() != null && dto.getName() != null) return auctionRepository.findAuctionsByTagsLikeAndNameLike(dto.getTags(), dto.getName());
        if(dto.getCategories() != null) return auctionRepository.findAuctionsByCategories(dto.getCategories());
        if(dto.getName() != null) return auctionRepository.findAuctionsByNameLike(dto.getName());
        if(dto.getTags() != null) return auctionRepository.findAuctionsByTags(dto.getTags());
        return auctionRepository.findActiveAuctions(PageRequest.of(0,20)).getContent();
    }

    public Auction findAuctionById(final long id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No auction exists for id: %d", id), Auction.class));
    }

    public Collection<Auction> getAuctionsByUserId(final long userId) {
        return auctionRepository.findByUserId(userId);
    }

    @Transactional(readOnly = false)
    public Auction updateAuction(Long id, Auction auction) {
        final Auction existing = findAuctionById(id);

        if (Guard.isSelf(existing.getUser().getId())) {
            throw new UnAuthorizedException("Cannot update the auction");
        }

        existing.setName(auction.getName());
        existing.setDescription(auction.getDescription());
        existing.setStartDate(auction.getStartDate());
        existing.setEndDate(auction.getEndDate());
        existing.setMinimalBid(auction.getMinimalBid());
        existing.setIsFeatured(auction.getIsFeatured());
        existing.setStatus(auction.getStatus());
        existing.setIsDeleted(auction.getIsDeleted());
        return auctionRepository.save(auction);
    }

    @Transactional(readOnly = false)
    public Auction patchAuction(Long id, Auction auction) {
        final Auction existing = findAuctionById(id);

        if (!Guard.isSelf(existing.getUser().getId()) && !Guard.isAdmin()) {
            throw new UnAuthorizedException("You are not allowed to cancel this auction");
        }

       if (auction.getName() != null) existing.setName(auction.getName());
       if (auction.getDescription() != null) existing.setDescription(auction.getDescription());
       if (auction.getStartDate() != null) existing.setStartDate(auction.getStartDate());
       if (auction.getEndDate() != null) existing.setEndDate(auction.getEndDate());
       if (auction.getMinimalBid() != null) existing.setMinimalBid(auction.getMinimalBid());
       if (auction.getIsFeatured() !=null) existing.setIsFeatured(auction.getIsFeatured());
       if (auction.getStatus() != null) existing.setStatus(auction.getStatus());
       if (auction.getIsDeleted() != null) existing.setIsDeleted(auction.getIsDeleted());
       return auctionRepository.save(existing);
    }

    @Transactional(readOnly = false)
    public Auction deleteAuction(Long id) {
        final Auction existing = findAuctionById(id);

        if (Guard.isSelf(existing.getUser().getId())) {
            throw new UnAuthorizedException("Cannot delete the auction");
        }

        existing.setIsDeleted(true);
        return auctionRepository.save(existing);
    }

    // todo: category crud:

    @Transactional(readOnly = false)
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Collection<Category> getAllCategories( ) {
        return categoryRepository.findAll();
    }
}













