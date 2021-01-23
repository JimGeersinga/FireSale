package com.FireSale.api.repository;

import com.FireSale.api.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    List<Tag> findByNameIn(List<String> names);

    List<Tag> findByNameContaining(String namePattern);
}
