package com.firesale.api.controller;


import com.firesale.api.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    byte[] getImageBytes(@PathVariable Long id) {
        return imageService.getFileBytes(id);
    }

}
