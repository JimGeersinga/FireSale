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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping(path = "/auctions", produces = MediaType.APPLICATION_JSON_VALUE)

public class AuctionController {

    private final AuctionService auctionService;
    private final AuctionMapper auctionMapper;


    // C
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@Valid @RequestBody AuctionDTO auctionDTO) {
        try {
            Auction auctionEntity = auctionMapper.DTOToEntity(auctionDTO);
            final Auction auction = auctionService.create(auctionEntity);
            return new ResponseEntity<>(new ApiResponse<>(true, auctionMapper.EntityToDTO(auction)), HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.AUCTION_CREATION_FAILED, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // R - TODO: Errormessage netjes maken
    @GetMapping
    public ResponseEntity all() {
        try {
            final Collection<Auction> auctions = auctionService.getAuctions();
            return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(auctionMapper::EntityToDTO)), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.UNKOWN, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // R
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") final long id) {
        final Optional<Auction> optionalAuction = auctionService.getAuctionById(id);
        //optionalAuction.orElse(); // TODO: hier iets moois van maken
        return new ResponseEntity<>(new ApiResponse<>(true, auctionMapper.EntityToDTO(optionalAuction)), HttpStatus.OK);
    }
    // U

    // D


}

