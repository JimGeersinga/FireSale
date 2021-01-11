package com.FireSale.api.controller;

import com.FireSale.api.dto.ApiResponse;
import com.FireSale.api.dto.auction.AuctionDTO;
import com.FireSale.api.dto.auction.AuctionFilterDTO;
import com.FireSale.api.dto.bid.BidDTO;
import com.FireSale.api.dto.bid.CreateBidDTO;
import com.FireSale.api.dto.auction.CreateAuctionDTO;
import com.FireSale.api.dto.auction.CreateImageDTO;
import com.FireSale.api.dto.user.PatchUserDTO;
import com.FireSale.api.mapper.AuctionMapper;
import com.FireSale.api.mapper.BidMapper;
import com.FireSale.api.model.Auction;
import com.FireSale.api.model.Bid;
import com.FireSale.api.service.AuctionService;
import com.FireSale.api.service.ImageService;
import com.FireSale.api.service.BidService;
import com.FireSale.api.service.UserService;
import com.FireSale.api.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping(path = "/auctions", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuctionController {

    private final AuctionService auctionService;
    private final UserService userService;
    private final AuctionMapper auctionMapper;
    private final ImageService imageService;
    private final RealTimeAuctionController bidController;
    final private BidService bidService;
    private final BidMapper bidMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@Valid @RequestBody CreateAuctionDTO createAuctionDTO) throws IOException{
        Collection<CreateImageDTO> images = createAuctionDTO.getImages();
        Auction auction = auctionService.create(createAuctionDTO);
        imageService.storeAuctionImages(images, auction);
        auction = auctionService.findAuctionById(auction.getId()); // retrieves the auction after the images have been added
        return new ResponseEntity<>(new ApiResponse<>(true, auctionMapper.toDTO(auction)), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity uploadAuctionImages(@RequestBody List<CreateImageDTO> imageDTOs, @PathVariable Long id) throws IOException { // todo: IO exception review?
        Auction auction = auctionService.findAuctionById(id);
        imageService.storeAuctionImages(imageDTOs, auction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{auctionId}/bids")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity placeBid(@PathVariable("auctionId") final long auctionId, @Valid @RequestBody CreateBidDTO createBidDTO) {
        Bid bid = bidMapper.toModel(createBidDTO);
        bid.setAuction(auctionService.findAuctionById(auctionId));
        bid.setUser(userService.findUserById(SecurityUtil.getSecurityContextUser().getUser().getId()));
        bid = bidService.create(bid);
        BidDTO bidDTO = bidMapper.toDTO(bid);
        bidController.sendBidNotification(auctionId, bidDTO);
        return new ResponseEntity<>(new ApiResponse<>(true, bidDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{auctionId}/bids")
    public ResponseEntity getBids(@PathVariable("auctionId") final long auctionId) {
        final List<Bid> bids = bidService.getForAuction(auctionId);
        return new ResponseEntity<>(new ApiResponse<>(true, bids.stream().map(bidMapper::toDTO).collect(Collectors.toList())), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity all() {
        final Collection<Auction> auctions = auctionService.getAuctions();
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(auctionMapper::toDTO)), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity started(@RequestParam(value = "page", required = false) Integer pageIndex,
                                  @RequestParam(value = "size", required = false) Integer pageSize) {
        final Collection<Auction> auctions = auctionService.getActiveAuctions(PageRequest.of(pageIndex == null?0 : pageIndex, pageSize == null? 100: pageSize));
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(auctionMapper::toDTO)), HttpStatus.OK);
    }



    @GetMapping("/featured")
    public ResponseEntity featured() {
        final Collection<Auction> auctions = auctionService.getFeatured();
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(auctionMapper::toDTO)), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity filter(@RequestBody AuctionFilterDTO filter) {
        final Collection<Auction> auctions = auctionService.filterAuctions(filter);
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(auctionMapper::toDTO)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") final long id) {
        final Auction auction = auctionService.findAuctionById(id);
        return new ResponseEntity<>(new ApiResponse<>(true, auctionMapper.toDTO(auction)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") final long id, @Valid @RequestBody final AuctionDTO auctionDTO) {
        auctionService.updateAuction(id, auctionMapper.toModel(auctionDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final long id) {
        auctionService.deleteAuction(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated() and (@guard.isAdmin() or @guard.isSelf(#userId))")
    public void patch(@PathVariable("id") final long id, @Valid @RequestBody final AuctionDTO auctionDTO) {
        auctionService.patchAuction(id, auctionMapper.toModel(auctionDTO));
    }
}
