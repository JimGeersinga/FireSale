package com.FireSale.api.service;

import com.FireSale.api.config.StorageProperties;
import com.FireSale.api.dto.auction.CreateImageDTO;
import com.FireSale.api.exception.StorageException;
import com.FireSale.api.model.Auction;
import com.FireSale.api.model.Image;
import com.FireSale.api.model.User;
import com.FireSale.api.repository.AuctionRepository;
import com.FireSale.api.repository.ImageRepository;
import com.FireSale.api.repository.UserRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
        // TODO: Controleren of dit echt nodig is want iets soortgelijks gebeurd ook onderaan in de init
        this.rootLocation = Paths.get(properties.getLocation());
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
        this.userService = userService;
        File uploadFolder = new File(String.valueOf(this.rootLocation));
        if (!uploadFolder.exists()) uploadFolder.mkdir();
    }

    @Transactional(readOnly = false)
    public void storeAuctionImages(List<CreateImageDTO> imageDTOs, Auction auction) {
        // TODO: Security toevoegen
        Set<Image> images = new HashSet<>();
        for (CreateImageDTO image : imageDTOs) {
            String outputFileName = UUID.randomUUID().toString() + image.getType();
            String subFolder = Paths.get("auctions", auction.getId().toString(), "images").toString();
            String filepath = Paths.get(this.rootLocation.toString(), subFolder, outputFileName).toString();
            File file = new File(filepath);

            try {
                FileUtils.writeByteArrayToFile(file, image.getImageB64());
            } catch (IOException e) {
                e.printStackTrace();
                // todo: foutmelding gooien dat conversie niet is gelukt
            }
            try {
                // Initialize a pointer
                // in file using OutputStream
                OutputStream os = new FileOutputStream(file);
                // Starts writing the bytes in it
                os.write(image.getImageB64());
                // Close the file
                os.close();

                Image newImage = new Image();
                newImage.setPath(filepath.substring(filepath.indexOf("\\auctions")).replace("\\","/"));
                newImage.setSort(image.getSort());
                newImage.setType(image.getType());
                images.add(newImage);
                this.imageRepository.save(newImage);

            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
            auction.setImages((Set<Image>) images);
            this.auctionRepository.save(auction);

//            try {
//                if (imagesDTO.isEmpty()) {
//                    throw new StorageException("Storing empty image failed.");
//                }
//                Path destinationFile = this.rootLocation.resolve(
//                        Paths.get(imagesDTO.getOriginalFilename()))
//                        .normalize().toAbsolutePath();
//                if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
//                    // TODO: Navragen wanneer dit voorkomt
//                    throw new StorageException("Storing an image outside of the directory is not permitted.");
//                }
//                try (InputStream inputStream = imagesDTO.getInputStream()) {
//                    Files.copy(inputStream, destinationFile,
//                            StandardCopyOption.REPLACE_EXISTING);
//                    // TODO: Filename zelf genereren (GUID)
//                }
//                // TODO: Wegschrijven naar database
//            } catch (IOException e) {
//                throw new StorageException("Storing of image failed.", e);
//            }
        }

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
        // TODO: Security toevoegen
        String outputFileName = "avatar." + imageDTO.getType();
        String subFolder = Paths.get("users", userId.toString(), "avatar").toString();
        Files.deleteIfExists(Paths.get(this.rootLocation.toString(), subFolder, outputFileName));
        String filepath = Paths.get(this.rootLocation.toString(), subFolder, outputFileName).toString();
        File file = new File(filepath); //TODO: in de try?
        try {
            FileUtils.writeByteArrayToFile(file, imageDTO.getImageB64());
        } catch (IOException e) {
            e.printStackTrace();
            // todo: foutmelding gooien dat conversie niet is gelukt
        }
        try {
            // Initialize a pointer
            // in file using OutputStream
            OutputStream os = new FileOutputStream(file);
            // Starts writing the bytes in it
            os.write(imageDTO.getImageB64());
            // Close the file
            os.close();

            User user = userService.findUserById(userId);
            Image avatar = user.getAvatar();
            if(avatar == null) {
                avatar = new Image();
                user.setAvatar(avatar);
            }
            avatar.setPath(filepath.substring(filepath.indexOf("\\users")).replace("\\","/"));
            avatar.setType(imageDTO.getType());
            imageRepository.save(avatar);
            userRepository.save(user);
        }
        catch(RuntimeException e)
        {
            System.out.println("Exception: " + e);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
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