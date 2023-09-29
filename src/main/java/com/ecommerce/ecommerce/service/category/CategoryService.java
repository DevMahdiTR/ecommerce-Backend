package com.ecommerce.ecommerce.service.category;

import com.ecommerce.ecommerce.dto.category.CategoryDTO;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTO;
import com.ecommerce.ecommerce.model.category.Category;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    ResponseEntity<Object> createCategory(@NotNull final Category category);
    ResponseEntity<Object> updateCategory(final long categoryId , @NotNull final Category category);
    ResponseEntity<Object> deleteCategoryById(final long categoryId);
    ResponseEntity<Object> addSubCategory(final long categoryId, @NotNull final SubCategory subCategory);
    ResponseEntity<Object> removeSubCategory(final long categoryId, final long subCategoryId) ;
    ResponseEntity<Object> fetchCategoryById(final long categoryId);
    ResponseEntity<Object> fetchAllCategories();
    ResponseEntity<Object> fetchAllSubCategoryInCategoryById(final long categoryId);
    public Category getCategoryById(final long categoryId);


}
