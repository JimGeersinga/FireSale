package com.FireSale.api.controller;

import com.FireSale.api.dto.ApiResponse;
import com.FireSale.api.dto.ErrorResponse;
import com.FireSale.api.dto.auction.AuctionDTO;
import com.FireSale.api.mapper.AuctionMapper;
import com.FireSale.api.model.Auction;
import com.FireSale.api.model.ErrorTypes;
import com.FireSale.api.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping(path = "/auctions", produces = MediaType.APPLICATION_JSON_VALUE)

public class AuctionController {

    private final AuctionService auctionService;
    private final AuctionMapper auctionMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@Valid @RequestBody AuctionDTO auctionDTO) {
        try {
            Auction auctionEntity = auctionMapper.toModel(auctionDTO);
            final Auction auction = auctionService.create(auctionEntity);
            return new ResponseEntity<>(new ApiResponse<>(true, auctionMapper.toDTO(auction)), HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.AUCTION_CREATION_FAILED, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity all() {
        try {
            final Collection<Auction> auctions = auctionService.getAuctions();
            return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(auctionMapper::toDTO)), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.AUCTION_NOT_FOUND, exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") final long id) {
        try {
            final Auction auction = auctionService.getAuctionById(id);
            return new ResponseEntity<>(new ApiResponse<>(true, auctionMapper.toDTO(auction)), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.AUCTION_NOT_FOUND, exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@guard.isSelf(#userId)")
    public ResponseEntity update(@PathVariable("id") final long id, @Valid @RequestBody final AuctionDTO auctionDTO) {
        try {
            final Auction auction = auctionService.updateAuction(id, auctionMapper.toModel(auctionDTO));
        } catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.AUCTION_NOT_FOUND, exception.getMessage()), HttpStatus.NOT_FOUND);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@guard.isSelf(#userId)")
    public void delete(@PathVariable("id") final long id) {
        auctionService.deleteAuction(id);
    }
}

