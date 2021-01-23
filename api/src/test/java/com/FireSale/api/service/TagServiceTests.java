package com.FireSale.api.service;

import com.FireSale.api.model.Address;
import com.FireSale.api.model.Tag;
import com.FireSale.api.model.User;
import com.FireSale.api.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    void shouldFindTagByNameSuccessfully(){

        final Tag givenTag = getTag();
        givenTag.setName("Auto");

        when(tagRepository.findByName(eq("Auto"))).thenReturn(Optional.of(givenTag));

        final Tag tag = tagService.getTagByName("Auto");
        assertThat(tag.getName()).isEqualTo(givenTag.getName());

        verify(tagRepository, times(2)).findByName(any(String.class));
    }

    private Tag getTag(){
        final Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Auto");
        return tag;
    }
}
