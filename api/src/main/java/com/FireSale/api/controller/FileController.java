package com.FireSale.api.controller;


import com.FireSale.api.exception.ResourceNotFoundException;
import com.FireSale.api.mapper.CategoryMapper;
import com.FireSale.api.model.Image;
import com.FireSale.api.repository.ImageRepository;
import com.FireSale.api.service.CategoryService;
import com.FireSale.api.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping(path = "/files", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController {
    private final ImageService imageService;

    @GetMapping(
            value = "/image/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getImageBytes(@PathVariable Long id) throws IOException {
        return imageService.getFileBytes(id);
    }

}
