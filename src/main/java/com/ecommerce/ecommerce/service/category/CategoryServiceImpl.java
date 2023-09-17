package com.ecommerce.ecommerce.service.category;

import com.ecommerce.ecommerce.dto.category.CategoryDTO;
import com.ecommerce.ecommerce.dto.category.CategoryDTOMapper;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTO;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTOMapper;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.category.Category;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import com.ecommerce.ecommerce.repository.CategoryRepository;
import com.ecommerce.ecommerce.service.subcategory.SubCategoryService;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryDTOMapper categoryDTOMapper;
    private final SubCategoryDTOMapper subCategoryDTOMapper ;
    private final SubCategoryService subCategoryService;
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryDTOMapper categoryDTOMapper, SubCategoryDTOMapper subCategoryDTOMapper, SubCategoryService subCategoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryDTOMapper = categoryDTOMapper;
        this.subCategoryDTOMapper = subCategoryDTOMapper;
        this.subCategoryService = subCategoryService;
    }

    @Override
    public CustomResponseEntity<CategoryDTO> createCategory(@NotNull final Category category) {
        final Category currentCategory = categoryRepository.save(category);
        final CategoryDTO categoryDTO = categoryDTOMapper.apply(currentCategory);
        return new CustomResponseEntity<>(HttpStatus.CREATED ,categoryDTO);
    }

    @Override
    public CustomResponseEntity<String> updateCategory(final long categoryId, @NotNull final Category categoryDetails) {
        final Category currentCategory = getCategoryById(categoryId);
        currentCategory.setTitle(categoryDetails.getTitle());
        final String successResponse = String.format("The Category with ID : %d updated successfully.",categoryId);
        return new CustomResponseEntity<>(HttpStatus.OK,successResponse);
    }

    @Override
    public CustomResponseEntity<String> deleteCategoryById(final long categoryId) {
        final Category currentCategory = getCategoryById(categoryId);
        categoryRepository.deleteCategoriesById(categoryId);

        final String successResponse = String.format("The Category with ID : %d deleted successfully.",categoryId);
        return new CustomResponseEntity<>(HttpStatus.OK, successResponse);
    }

    @Override
    public CustomResponseEntity<CategoryDTO> addSubCategory(final long categoryId, @NotNull final SubCategory subCategory) {
        final Category currentSubCategory =  getCategoryById(categoryId);
        subCategory.setCategory(currentSubCategory);
        currentSubCategory.getSubCategories().add(subCategory);
        categoryRepository.save(currentSubCategory);
        final CategoryDTO category = categoryDTOMapper.apply(currentSubCategory);
        return new CustomResponseEntity<>(HttpStatus.OK , category);
    }

    @Override
    public CustomResponseEntity<CategoryDTO> removeSubCategory(final long categoryId, final long subCategoryId) {
        final Category currentCategory = getCategoryById(categoryId);
        final SubCategory currentSubCategory = subCategoryService.getSubCategoryById(subCategoryId);

        if(!currentCategory.getSubCategories().contains(currentSubCategory))
        {
            throw new IllegalStateException("The Category does not have this sub category ");
        }

        currentCategory.getSubCategories().remove(currentSubCategory);
        currentSubCategory.setArticles(null);
        subCategoryService.deleteSubCategoryById(currentSubCategory.getId());
        categoryRepository.save(currentCategory);
        final CategoryDTO category  = categoryDTOMapper.apply(currentCategory);

        return new CustomResponseEntity<>(HttpStatus.OK , category);

    }

    @Override
    public CustomResponseEntity<List<CategoryDTO>> fetchAllCategories() {
        final List<Category> currentCategories = categoryRepository.fetchAllCategory();
        final List<CategoryDTO> categories = currentCategories.stream().map(categoryDTOMapper).toList();
        return new CustomResponseEntity<>(HttpStatus.OK , categories);
    }

    @Override
    public CustomResponseEntity<List<SubCategoryDTO>> fetchAllSubCategoryInCategoryById(final long categoryId) {
        final Category currentCategory = getCategoryById(categoryId);
        final List<SubCategoryDTO> subCategories = currentCategory.getSubCategories().stream().map(subCategoryDTOMapper).toList();
        return new CustomResponseEntity<>(HttpStatus.OK , subCategories);
    }

    @Override
    public Category getCategoryById(final long categoryId)
    {
        return categoryRepository.fetchCategoryById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("The Category with ID : %d could not be found in our system.", categoryId))
        );
    }
}