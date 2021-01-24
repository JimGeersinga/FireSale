package com.FireSale.api.service;

import com.FireSale.api.repository.CategoryRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;
}
