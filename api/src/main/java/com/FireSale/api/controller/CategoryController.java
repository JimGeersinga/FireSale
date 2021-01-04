package com.FireSale.api.controller;

import com.FireSale.api.dto.ApiResponse;
import com.FireSale.api.dto.category.UpsertCategoryDTO;
import com.FireSale.api.mapper.CategoryMapper;
import com.FireSale.api.model.Category;
import com.FireSale.api.service.CategoryService;
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
@RequestMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity available() {
        final Collection<Category> auctions = categoryService.getAvailableCategories();
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(categoryMapper::toDTO)), HttpStatus.OK);
    }

    @GetMapping("archived")
    @PreAuthorize("isAuthenticated() and @guard.isAdmin()")
    public ResponseEntity archived() {
        final Collection<Category> auctions = categoryService.getArchivedCategories();
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(categoryMapper::toDTO)), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated() and @guard.isAdmin()")
    public ResponseEntity create(@Valid @RequestBody UpsertCategoryDTO upsertCategoryDTO) {
        final Category category = categoryService.create(categoryMapper.toModel(upsertCategoryDTO));
        return new ResponseEntity<>(new ApiResponse<>(true, categoryMapper.toDTO(category)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated() and @guard.isAdmin()")
    public void update(@PathVariable("id") final long id, @Valid @RequestBody final UpsertCategoryDTO upsertCategoryDTO) {
        categoryService.updateCategory(id, categoryMapper.toModel(upsertCategoryDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() and @guard.isAdmin()")
    public void delete(@PathVariable("id") final long id) {
        categoryService.deleteCategory(id);
    }

}
