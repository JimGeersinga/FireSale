package com.FireSale.api.service;

import com.FireSale.api.exception.ResourceNotFoundException;
import com.FireSale.api.model.Tag;
import com.FireSale.api.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTests {

    @Mock
    TagRepository tagRepository;

    @InjectMocks
    TagService tagService;

    @Test
    void shouldFindAllTagsSuccessfully(){

        List<Tag> givenAllTags = tagService.getAllTags();
        assertNotNull(givenAllTags);

        verify(tagRepository).findAll();
    }

    @Test
    void shouldFindTagByNameSuccessfully(){

        final Tag givenTag = getTag();
        givenTag.setName("Auto");

        when(tagRepository.findByName(eq("Auto"))).thenReturn(Optional.of(givenTag));

        final Tag tag = tagService.getTagByName("Auto");
        assertThat(tag.getName()).isEqualTo(givenTag.getName());

        verify(tagRepository, times(2)).findByName(any(String.class));
    }

    @Test
    void shouldCreateTagSuccessfully() {
        final Tag tag = getTag();
        tag.setId(null);

        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        Tag savedTag = tagService.createTag("Boot");

        assertThat(savedTag).isNotNull();

        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    void shouldDeleteTagSuccessfully() {
        final Tag tag = getTag();
        Optional<Tag> optionalTag = Optional.of(tag);

        when(tagRepository.findByName("Auto")).thenReturn(optionalTag);
        tagService.deleteTag(tag.getName());

        verify(tagRepository).delete(tag);
    }

    @Test
    void exceptionShouldBeThrownWhenTryingToDeleteNonExistingTag() {
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> tagService.deleteTag("Scooter"));

        String expectedMessage = "Resource was not found: [No tag exists with name: {0}]";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(tagRepository).findByName(any(String.class));
        verify(tagRepository, times(0)).delete(any(Tag.class));
    }

    private Tag getTag(){
        final Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Auto");
        return tag;
    }
}
