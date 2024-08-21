package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByNameCategory(String categoryName);
}
