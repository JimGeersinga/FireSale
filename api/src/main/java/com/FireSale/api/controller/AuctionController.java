package com.FireSale.api.controller;

import com.FireSale.api.dto.ApiResponse;
import com.FireSale.api.dto.auction.AuctionDTO;
import com.FireSale.api.dto.auction.CreateAuctionDTO;
import com.FireSale.api.mapper.AuctionMapper;
import com.FireSale.api.model.Auction;
import com.FireSale.api.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity create(@Valid @RequestBody CreateAuctionDTO createAuctionDTO) {
        final Auction auction = auctionService.create(createAuctionDTO);
        return new ResponseEntity<>(new ApiResponse<>(true, auctionMapper.toDTO(auction)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity all() {
        final Collection<Auction> auctions = auctionService.getAuctions();
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(auctionMapper::toDTO)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") final long id) {
        final Auction auction = auctionService.getAuctionById(id);
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

}
