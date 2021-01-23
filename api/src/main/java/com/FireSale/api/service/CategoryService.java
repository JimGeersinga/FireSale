package com.FireSale.api.service;

import com.FireSale.api.exception.ResourceNotFoundException;
import com.FireSale.api.model.Category;
import com.FireSale.api.model.ErrorTypes;
import com.FireSale.api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Collection<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(final long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No category exists for id: %d", id), ErrorTypes.CATEGORY_NOT_FOUND));
    }

    @Transactional(readOnly = false)
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = false)
    public Category updateCategory(Long id, Category category) {
        final Category existing = getCategoryById(id);
        existing.setName(category.getName());
        existing.setArchived(category.getArchived());
        Category c = categoryRepository.save(existing);
        return c;
    }

    @Transactional(readOnly = false)
    public void deleteCategory(Long id) {
        final Category existing = getCategoryById(id);
        if (existing.getAuctions().size() != 0) {
            existing.setArchived(true);
            categoryRepository.save(existing);
        } else {
            categoryRepository.delete(existing);
        }
    }

    public Collection<Category> getAvailableCategories() {
        return categoryRepository.findByArchived(false);
    }

    public Collection<Category> getArchivedCategories() {
        return categoryRepository.findByArchived(true);
    }
}
