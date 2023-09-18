package com.ecommerce.ecommerce.controller.category;


import com.ecommerce.ecommerce.dto.category.CategoryDTO;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTO;
import com.ecommerce.ecommerce.model.category.Category;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import com.ecommerce.ecommerce.service.category.CategoryService;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public CustomResponseEntity<CategoryDTO> createCategory(@NotNull @Valid @RequestBody final Category category)
    {
        return categoryService.createCategory(category);
    }


    @PutMapping("/{categoryId}")
    public CustomResponseEntity<String> updateCategory(@PathVariable("categoryId")final long categoryId, @NotNull @Valid @RequestBody final Category categoryDetails)
    {
        return categoryService.updateCategory(categoryId , categoryDetails);
    }

    @PutMapping("/{categoryId}/subcategories")
    public CustomResponseEntity<CategoryDTO> addSubCategory(@PathVariable("categoryId") final long categoryId , @NotNull @Valid @RequestBody final SubCategory subCategory)
    {
        return categoryService.addSubCategory(categoryId , subCategory);
    }

    @PutMapping("/{categoryId}/subcategories/{subCategoryId}")
    public CustomResponseEntity<CategoryDTO> removeSubCategory(@PathVariable("categoryId") final long categoryId, @PathVariable("subCategoryId") final long subCategoryId)
    {
        return categoryService.removeSubCategory(categoryId,subCategoryId);
    }

    @GetMapping("/{categoryId}")
    public CustomResponseEntity<CategoryDTO> fetchCategoryById(@PathVariable("categoryId") final long categoryId)
    {
        return categoryService.fetchCategoryById(categoryId);
    }

    @GetMapping()
    public CustomResponseEntity<List<CategoryDTO>> fetchAllCategories()
    {
        return categoryService.fetchAllCategories();
    }

    @GetMapping("/{categoryId}/subcategories")
    public CustomResponseEntity<List<SubCategoryDTO>> fetchAllSubCategoryInCategoryById(@PathVariable("categoryId") final long categoryId)
    {
        return categoryService.fetchAllSubCategoryInCategoryById(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    public CustomResponseEntity<String> deleteCategory(@PathVariable("categoryId")final long categoryId)
    {
        return categoryService.deleteCategoryById(categoryId);
    }

}
