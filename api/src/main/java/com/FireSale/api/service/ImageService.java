package com.FireSale.api.service;

import com.FireSale.api.config.StorageProperties;
import com.FireSale.api.dto.auction.CreateImageDTO;
import com.FireSale.api.exception.ResourceNotFoundException;
import com.FireSale.api.exception.StorageException;
import com.FireSale.api.model.Auction;
import com.FireSale.api.model.Image;
import com.FireSale.api.model.User;
import com.FireSale.api.repository.AuctionRepository;
import com.FireSale.api.repository.ImageRepository;
import com.FireSale.api.repository.UserRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class ImageService {
    private final Path rootLocation;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final UserService userService;

    @Autowired
    public ImageService(StorageProperties properties, ImageRepository imageRepository, UserRepository userRepository, AuctionRepository auctionRepository, UserService userService) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
        this.userService = userService;
        File uploadFolder = new File(String.valueOf(this.rootLocation));
        if (!uploadFolder.exists()) uploadFolder.mkdir();
    }



    public byte[] getFileBytes(long id) throws IOException {
        var image = this.imageRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Image should be in the database but it is not"),Image.class));
        String filePath =  Paths.get(this.rootLocation.toString(), image.getPath()).toString();
        return FileUtils.readFileToByteArray(new File(filePath));
    }


    public Image saveImage(CreateImageDTO imageDTO, Path storePath) throws IOException
    {
        Image newImage = new Image();
        if(imageDTO.getId() != null)
        {
            newImage = this.imageRepository.findById(imageDTO.getId()).orElseThrow(() ->
            new ResourceNotFoundException(String.format("Image should be in the database but it is not"),Image.class));
            Files.deleteIfExists(Paths.get(this.rootLocation.toString(), newImage.getPath()));
        }
        File file = new File(storePath.toString());
        try {
            FileUtils.writeByteArrayToFile(file, imageDTO.getImageB64());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // actual file
            OutputStream os = new FileOutputStream(file);
            os.write(imageDTO.getImageB64());
            os.close();
            // new image
            newImage.setPath(this.rootLocation.relativize(storePath).toString());
            newImage.setSort(imageDTO.getSort());
            newImage.setType(imageDTO.getType());
            this.imageRepository.save(newImage);
            return newImage;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }


    @Transactional(readOnly = false)
    public void storeAuctionImages(List<CreateImageDTO> imageDTOs, Auction auction) throws IOException {
        Set<Image> images = new HashSet<>();
        for (CreateImageDTO image : imageDTOs) {
            String outputFileName = UUID.randomUUID().toString() + image.getType();
            String subFolder = Paths.get("auctions", auction.getId().toString(), "images").toString();
            Path filepath = Paths.get(this.rootLocation.toString(), subFolder, outputFileName);
            var newImage = saveImage(image, filepath);
            if(newImage != null)
            {
                images.add(newImage);
            }
        }
        auction.setImages((Set<Image>) images);
        this.auctionRepository.save(auction);
    }

    @Transactional(readOnly = true)
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Transactional(readOnly = true)
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }
    @Transactional(readOnly = false)
    public void storeAvatar(CreateImageDTO imageDTO, Long userId) throws IOException {
        String outputFileName = "avatar" + imageDTO.getType();
        String subFolder = Paths.get("users", userId.toString(), "avatar").toString();
        Path filepath = Paths.get(this.rootLocation.toString(), subFolder, outputFileName);
        var avatar = saveImage(imageDTO, filepath);
        if(avatar != null)
        {
            User user = userService.findUserById(userId);
            user.setAvatar(avatar);
            userRepository.save(user);
        }
    }

    @Transactional(readOnly = false)
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Transactional(readOnly = false)
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}