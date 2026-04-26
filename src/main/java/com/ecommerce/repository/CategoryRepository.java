package com.ecommerce.repository;

import com.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByIsActiveTrue();

    Optional<Category> findByNameAndIsActiveTrue(String name);

    List<Category> findByParentCategoryIsNullAndIsActiveTrue();

    @Query("SELECT c FROM Category c WHERE c.parentCategory.id = :parentId AND c.isActive = true")
    List<Category> findSubCategoriesByParentId(@Param("parentId") Long parentId);
}