package com.firesale.api.controller;

import com.firesale.api.dto.ApiResponse;
import com.firesale.api.dto.auction.*;
import com.firesale.api.dto.bid.BidDTO;
import com.firesale.api.dto.bid.CreateBidDTO;
import com.firesale.api.exception.UnAuthorizedException;
import com.firesale.api.mapper.AuctionMapper;
import com.firesale.api.mapper.BidMapper;
import com.firesale.api.model.Auction;
import com.firesale.api.model.AuctionStatus;
import com.firesale.api.model.Bid;
import com.firesale.api.service.*;
import com.firesale.api.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping(path = "/auctions", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuctionController {

    private final AuctionService auctionService;
    private final UserService userService;
    private final AuctionMapper auctionMapper;
    private final ImageService imageService;
    private final AuctionNotificationService auctionNotificationService;
    private final BidService bidService;
    private final BidMapper bidMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@Valid @RequestBody CreateAuctionDTO createAuctionDTO) {
        Collection<CreateImageDTO> images = createAuctionDTO.getImages();
        Auction auction = auctionService.create(createAuctionDTO);
        imageService.storeAuctionImages(images, auction);
        auction = auctionService.findAuctionById(auction.getId()); // retrieves the auction after the images have been
        // added
        return new ResponseEntity<>(new ApiResponse<>(true, auctionMapper.toDTO(auction)), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity uploadAuctionImages(@RequestBody List<CreateImageDTO> imageDTOs, @PathVariable Long id) {
        Auction auction = auctionService.findAuctionById(id);
        imageService.storeAuctionImages(imageDTOs, auction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{auctionId}/bids")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity placeBid(@PathVariable("auctionId") final long auctionId,
                                      @Valid @RequestBody CreateBidDTO createBidDTO) {
        Bid bid = bidMapper.toModel(createBidDTO);
        bid.setAuction(auctionService.findAuctionById(auctionId));
        bid.setUser(userService.findUserById(SecurityUtil.getSecurityContextUser().getUser().getId()));
        bid = bidService.create(bid);
        BidDTO bidDTO = bidMapper.toDTO(bid);
        auctionNotificationService.sendBidNotification(auctionId, bidDTO);
        return new ResponseEntity<>(new ApiResponse<>(true, bidDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{auctionId}/bids")
    public ResponseEntity getBids(@PathVariable("auctionId") final long auctionId) {
        final List<Bid> bids = bidService.getForAuction(auctionId);
        return new ResponseEntity<>(
                new ApiResponse<>(true, bids.stream().map(bidMapper::toDTO).collect(Collectors.toList())),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity all() {
        final Collection<Auction> auctions = auctionService.getAuctions();
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(x -> auctionMapper.toDTO(x, auctionService)).collect(Collectors.toList())),
                HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity started(@RequestParam(value = "page", required = false) Integer pageIndex,
                                  @RequestParam(value = "size", required = false) Integer pageSize) {
        final Collection<Auction> auctions = auctionService.getActiveAuctions(
                PageRequest.of(pageIndex == null ? 0 : pageIndex, pageSize == null ? 100 : pageSize));
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(x -> auctionMapper.toDTO(x, auctionService))),
                HttpStatus.OK);
    }

    @GetMapping("/featured")
    public ResponseEntity featured() {
        final Collection<Auction> auctions = auctionService.getFeatured();
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(x -> auctionMapper.toDTO(x, auctionService))),
                HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity filter(@RequestBody AuctionFilterDTO filter) {
        final Collection<Auction> auctions = auctionService.filterAuctions(filter);
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(x -> auctionMapper.toDTO(x, auctionService))),
                HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/favourite")
    public ResponseEntity favourite() {
        final Collection<Auction> auctions = auctionService.getFavourites(SecurityUtil.getSecurityContextUser().getUser().getId());
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(x -> auctionMapper.toDTO(x, auctionService))),
                HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/won")
    public ResponseEntity won() {
        final Collection<Auction> auctions = auctionService.getWonAuction(SecurityUtil.getSecurityContextUser().getUser().getId());
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(x -> auctionMapper.toDTO(x, auctionService))),
                HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/bidded")
    public ResponseEntity bidded() {
        final Collection<Auction> auctions = auctionService.getByUserBid(SecurityUtil.getSecurityContextUser().getUser().getId());
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(x -> auctionMapper.toDTO(x, auctionService))),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") final long id) {
        final Auction auction = auctionService.findAuctionById(id);
        return new ResponseEntity<>(new ApiResponse<>(true, auctionMapper.toDTO(auction, auctionService)), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/winningInformation")
    public ResponseEntity getWinningInformation(@PathVariable("id") final long id) {
        final Auction auction = auctionService.findAuctionById(id);
        Long loggedInUserId = SecurityUtil.getSecurityContextUser().getUser().getId();
        if (auction.getStatus() == AuctionStatus.CLOSED && auction.getFinalBid() != null && (auction.getFinalBid().getUser().getId().equals(loggedInUserId) || auction.getUser().getId().equals(loggedInUserId))) {
            return new ResponseEntity<>(new ApiResponse<>(true, auctionMapper.toWinningInfo(auction)), HttpStatus.OK);
        }
        throw new UnAuthorizedException("Winning information may not yet be retrieved");

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") final long id, @Valid @RequestBody final CreateAuctionDTO createAuctionDTO) {
        Collection<CreateImageDTO> images = createAuctionDTO.getImages();
        Auction auction = auctionService.updateAuction(id, createAuctionDTO);
        imageService.storeAuctionImages(images, auction);
    }

    @PostMapping("/{id}/favourite")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void favourite(@PathVariable("id") final long id, @Valid @RequestBody IsFavouriteDTO favourite) {
        auctionService.toggleFavourite(id, SecurityUtil.getSecurityContextUser().getUser().getId(), favourite.getIsFavourite());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated() and (@guard.isAdmin() or @guard.isSelf(#userId))")
    public void patch(@PathVariable("id") final long id, @Valid @RequestBody final AuctionDTO auctionDTO) {
        auctionService.patchAuction(id, auctionMapper.toModel(auctionDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void delete(@PathVariable("id") final long id) {
        auctionService.deleteAuction(id);
    }
}
