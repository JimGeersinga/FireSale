package com.firesale.api.controller;

import com.firesale.api.dto.ApiResponse;
import com.firesale.api.mapper.TagMapper;
import com.firesale.api.model.Tag;
import com.firesale.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity all(@RequestParam(required = false, name = "searchterm") String searchTerm) {
        List<Tag> tags;
        if (searchTerm == null) {
            tags = tagService.getAllTags();
        } else {
            tags = tagService.searchTagsByName(searchTerm);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, tags.stream().map(tagMapper::toDTO)), HttpStatus.OK);
    }
}
