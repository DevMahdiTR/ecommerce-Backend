package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CategoryRepository extends JpaRepository<Category , Integer> {

    @Query(value = "SELECT C FROM Category  C WHERE C.id = :categoryId")
    Optional<Category> fetchCategoryById(@Param("categoryId") final long categoryId);

    @Query(value = "SELECT C FROM Category  C WHERE C.title = :categoryTitle")
    Optional<Category> fetchCategoryByTitle(@Param("categoryTitle") final long categoryTitle);

    @Query(value = "SELECT C FROM Category C")
    List<Category> fetchAllCategory();

    @Transactional
    @Modifying
    @Query("DELETE Category C WHERE C.id = :categoryId")
    void deleteCategoriesById(@Param("categoryId") final long categoryId);

}
