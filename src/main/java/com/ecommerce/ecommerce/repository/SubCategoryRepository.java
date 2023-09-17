package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface SubCategoryRepository extends JpaRepository<SubCategory , Integer> {

    @Query(value = "select sc from SubCategory sc where sc.id = :subCategoryId")
    Optional<SubCategory> fetchSubCategoryById(@Param("subCategoryId") final long subCategoryId);



    @Transactional
    @Modifying
    @Query("delete from SubCategory sc where sc.id = :subCategoryId")
    void deleteSubCategoriesById(@Param("subCategoryId")final long subCategoryId);

}
