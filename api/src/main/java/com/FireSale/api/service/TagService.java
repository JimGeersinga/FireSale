package com.FireSale.api.service;

import com.FireSale.api.exception.ResourceNotFoundException;
import com.FireSale.api.model.ErrorTypes;
import com.FireSale.api.model.Tag;
import com.FireSale.api.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> searchTagsByName(String searchTerm) {
        return tagRepository.findByNameContaining(searchTerm);
    }

    public Tag getTagByName(String name) {
        if (tagRepository.findByName(name).isEmpty()) {
            return null;
        } else {
            return tagRepository.findByName(name)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("No tag exists with name: %s", name), ErrorTypes.TAG_NOT_FOUND));
        }
    }

    public List<Tag> findByNameIn(List<String> names) {
        return tagRepository.findByNameIn(names);
    }

    @Transactional(readOnly = false)
    public Tag createTag(String name) {
        final Tag tag = new Tag();
        tag.setName(name);
        return tagRepository.save(tag);
    }

    @Transactional(readOnly = false)
    public void deleteTag(String name) {
        final Tag existing = tagRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No tag exists with name: %s", name), ErrorTypes.TAG_NOT_FOUND));
        tagRepository.delete(existing);
    }
}
