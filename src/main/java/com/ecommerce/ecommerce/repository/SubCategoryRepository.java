package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.subcategory.SubCategory;
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
public interface SubCategoryRepository extends JpaRepository<SubCategory , Integer> {

    @Query(value = "select s from SubCategory s where s.id = :subCategoryId")
    Optional<SubCategory> fetchSubCategoryById(@Param("subCategoryId") final long subCategoryId);



    @Transactional
    @Modifying
    @Query("delete from SubCategory s where s.id = :subCategoryId")
    void deleteSubCategoriesById(@Param("subCategoryId")final long subCategoryId);

    @Modifying
    @Transactional
    @Query("DELETE FROM SubCategory s WHERE s IN :subCategories")
    void deleteSubCategoriesByIds(List<SubCategory> subCategories);

}
