package com.FireSale.api.service;

import com.FireSale.api.exception.ResourceNotFoundException;
import com.FireSale.api.exception.UnAuthorizedException;
import com.FireSale.api.mapper.AuctionMapper;
import com.FireSale.api.model.Auction;
import com.FireSale.api.model.AuctionStatus;
import com.FireSale.api.model.User;
import com.FireSale.api.repository.AuctionRepository;
import com.FireSale.api.repository.UserRepository;
import com.FireSale.api.security.Guard;
import com.FireSale.api.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public Auction create(Auction auction) {
        final User user = userRepository.getOne(SecurityUtil.getSecurityContextUser().getUser().getId());

        auction.setUser(user);
        auction.setIsDeleted(false);
        auction.setIsFeatured(false);
        auction.setStatus(AuctionStatus.Ready);

        return auctionRepository.save(auction);
    }

    public Collection<Auction> getAuctions() {
        return auctionRepository.findAll();
    }

    public Auction getAuctionById(final long id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No auction exists for id: %d", id), Auction.class));
    }

    @Transactional(readOnly = false)
    public Auction updateAuction(Long id, Auction auction) {
        final Auction existing = getAuctionById(id);

        if(Guard.isSelf(existing.getUser().getId())) {
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
        final Auction existing = getAuctionById(id);

        if(Guard.isSelf(existing.getUser().getId())) {
            throw new UnAuthorizedException("Cannot delete the auction");
        }

        existing.setIsDeleted(true);
        return auctionRepository.save(existing);
    }
}













