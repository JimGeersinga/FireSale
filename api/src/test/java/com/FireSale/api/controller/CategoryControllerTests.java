package com.firesale.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firesale.api.dto.category.CategoryDTO;
import com.firesale.api.exception.GlobalExceptionHandler;
import com.firesale.api.mapper.CategoryMapper;
import com.firesale.api.model.Category;
import com.firesale.api.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTests {
    private MockMvc mvc;

    @Mock
    private CategoryService categoryService;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryController categoryController;

    private JacksonTester<CategoryDTO> categoryDTOJacksonTester;
    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        // given

        Category c = new Category();
        c.setName("test");
        c.setId(1L);

        when(categoryMapper.toDTO(any(Category.class))).thenAnswer((i)->{
            var category = (Category)i.getArguments()[0];
            var dto = new CategoryDTO();
            dto.setName(category.getName());
            return dto;
        });



        given(categoryService.getAvailableCategories())
                .willReturn(Arrays.asList(c));
        // when
        MockHttpServletResponse response = mvc.perform(
                 get("/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("test");
    }
}
