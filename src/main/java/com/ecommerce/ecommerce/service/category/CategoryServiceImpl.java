package com.ecommerce.ecommerce.service.category;

import com.ecommerce.ecommerce.dto.category.CategoryDTO;
import com.ecommerce.ecommerce.dto.category.CategoryDTOMapper;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTO;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.category.Category;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import com.ecommerce.ecommerce.repository.CategoryRepository;
import com.ecommerce.ecommerce.service.subcategory.SubCategoryService;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryDTOMapper categoryDTOMapper;
    private final SubCategoryService subCategoryService;


    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryDTOMapper categoryDTOMapper , SubCategoryService subCategoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryDTOMapper = categoryDTOMapper;
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
        categoryRepository.save(currentCategory);
        final String successResponse = String.format("The Category with ID : %d updated successfully.",categoryId);
        return new CustomResponseEntity<>(HttpStatus.OK,successResponse);
    }

    @Transactional
    @Override
    public CustomResponseEntity<String> deleteCategoryById(final long categoryId) {
        final Category currentCategory = getCategoryById(categoryId);
        final List<SubCategory> subCategories = currentCategory.getSubCategories();

        if (subCategories.size() > 0) {
            subCategoryService.deleteSubCategoryAll(currentCategory.getSubCategories());
        }

        categoryRepository.deleteCategoriesById(categoryId);

        final String successResponse = String.format("The Category with ID : %d deleted successfully.",categoryId);
        return new CustomResponseEntity<>(HttpStatus.OK, successResponse);
    }

    @Override
    public CustomResponseEntity<String> addSubCategory(final long categoryId, @NotNull final SubCategory subCategory) {
        final Category currentCategory =  getCategoryById(categoryId);
        subCategory.setCategory(currentCategory);
        currentCategory.getSubCategories().add(subCategory);
        categoryRepository.save(currentCategory);
        final String successResponse = String.format("The Sub category with TITLE : %s added successfully",subCategory.getTitle());
        return new CustomResponseEntity<>(HttpStatus.OK , successResponse);
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
    public CustomResponseEntity<CategoryDTO> fetchCategoryById(final long categoryId) {
        final Category currentCategory = getCategoryById(categoryId);
        final CategoryDTO category = categoryDTOMapper.apply(currentCategory);
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
        final List<SubCategoryDTO> subCategories = subCategoryService.mapToDTOList(currentCategory.getSubCategories());
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
