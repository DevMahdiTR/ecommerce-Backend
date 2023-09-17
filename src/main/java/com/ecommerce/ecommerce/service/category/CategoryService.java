package com.ecommerce.ecommerce.service.category;

import com.ecommerce.ecommerce.dto.category.CategoryDTO;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTO;
import com.ecommerce.ecommerce.model.category.Category;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CategoryService {

    CustomResponseEntity<CategoryDTO> createCategory(@NotNull final Category category);
    CustomResponseEntity<String> updateCategory(final long categoryId , @NotNull final Category category);
    CustomResponseEntity<String> deleteCategoryById(final long categoryId);
    CustomResponseEntity<CategoryDTO> addSubCategory(final long categoryId, @NotNull final SubCategory subCategory);
    public CustomResponseEntity<CategoryDTO> removeSubCategory(final long categoryId, final long subCategoryId) ;
    CustomResponseEntity<List<CategoryDTO>> fetchAllCategories();
    CustomResponseEntity<List<SubCategoryDTO>> fetchAllSubCategoryInCategoryById(final long categoryId);
    public Category getCategoryById(final long categoryId);


}
