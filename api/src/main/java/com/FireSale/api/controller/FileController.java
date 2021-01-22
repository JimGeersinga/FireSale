package com.FireSale.api.controller;


import com.FireSale.api.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping(path = "/file", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController {
    private final ImageService imageService;

    @GetMapping(
            value = "/image/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImageBytes(@PathVariable Long id) {
        return imageService.getFileBytes(id);
    }

}
