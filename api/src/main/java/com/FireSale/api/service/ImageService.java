package com.FireSale.api.service;

import com.FireSale.api.dto.auction.CreateImageDTO;
import com.FireSale.api.exception.ResourceNotFoundException;
import com.FireSale.api.model.Auction;
import com.FireSale.api.model.ErrorTypes;
import com.FireSale.api.model.Image;
import com.FireSale.api.model.User;
import com.FireSale.api.repository.AuctionRepository;
import com.FireSale.api.repository.ImageRepository;
import com.FireSale.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final UserService userService;

    @Autowired
    public ImageService(ImageRepository imageRepository, UserRepository userRepository, AuctionRepository auctionRepository, UserService userService) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
        this.userService = userService;
    }

    public byte[] getFileBytes(long id) {
        var image = this.imageRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Image should be in the database but it is not"), ErrorTypes.IMAGE_NOT_FOUND));
        return Base64.getDecoder().decode(image.getPath());
    }

    public Image saveImage(CreateImageDTO imageDTO) {
        Image newImage = new Image();
        if (imageDTO.getId() != null) {
            newImage = this.imageRepository.findById(imageDTO.getId()).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Image should be in the database but it is not"), ErrorTypes.IMAGE_NOT_FOUND));
        }

        newImage.setPath(imageDTO.getPath());
        newImage.setSort(imageDTO.getSort());
        newImage.setType(imageDTO.getType());
        this.imageRepository.save(newImage);
        return newImage;

    }

    @Transactional(readOnly = false)
    public void storeAuctionImages(Collection<CreateImageDTO> imageDTOs, Auction auction) {
        Set<Image> images = new HashSet<>();
        for (CreateImageDTO image : imageDTOs) {
            var newImage = saveImage(image);
            if (newImage != null) {
                images.add(newImage);
            }
        }
        auction.setImages(images);
        this.auctionRepository.save(auction);
    }


    @Transactional(readOnly = false)
    public void storeAvatar(CreateImageDTO imageDTO, Long userId) {
        var avatar = saveImage(imageDTO);
        if (avatar != null) {
            User user = userService.findUserById(userId);
            user.setAvatar(avatar);
            userRepository.save(user);
        }
    }
}