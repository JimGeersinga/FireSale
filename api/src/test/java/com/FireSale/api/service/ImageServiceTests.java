package com.firesale.api.service;

import com.firesale.api.repository.AuctionRepository;
import com.firesale.api.repository.ImageRepository;
import com.firesale.api.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTests {
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuctionRepository auctionRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private ImageService imageService;


//    @Test
//    @DisplayName("Test deleteCategory Failure")
//    void deleteCategoryFailure() {
//        // Setup mock repository
//        doReturn(Optional.ofNullable(null)).when(categoryRepository).findById(1L);
//        String expected = String.format("Resource was not found: [No category exists for id: %d]", 1L);
//        // Execute service call
//        var returned = Assertions.assertThrows(ResourceNotFoundException.class, () ->categoryService.deleteCategory(1L));
//        String actual = returned.getMessage();
//        // Assert response
//        verify(categoryRepository).findById(1L);
//        Assertions.assertTrue(actual.equals(expected), "Error message is incorrect");
//    }
//
//    @Test
//    @DisplayName("Test deleteCategoryFilled Success")
//    void deleteCategoryFilled() {
//        // Setup mock repository
//        var category = this.getFilled(1L);
//        when(categoryRepository.save(any(Category.class))).thenAnswer((answer) -> answer.getArguments()[0]);
//        doReturn(Optional.of(category)).when(categoryRepository).findById(1L);
//
//        // Execute service call
//        categoryService.deleteCategory(1L);
//
//        // Assert response
//        verify(categoryRepository).save(any(Category.class));
//    }
//
//    private Image getImage()
//    {
//        Image img = new Image();
//        img.setId(1L);
//        img.setType(".jpg");
//        img.set
//    }

    private String getBase64()
    {
        return "";
    }



}
