package com.FireSale.api.service;

import com.FireSale.api.dto.TagDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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













