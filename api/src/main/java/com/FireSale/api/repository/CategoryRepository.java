package com.FireSale.api.repository;

import com.FireSale.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


//    @Query("SELECT c FROM Category c WHERE c.id in :ids")
    Set<Category> findByIdIn(List<Long> ids);
}