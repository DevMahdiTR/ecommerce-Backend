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
import com.ecommerce.ecommerce.utility.responses.ResponseHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryDTOMapper categoryDTOMapper;
    private final SubCategoryService subCategoryService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryDTOMapper categoryDTOMapper , SubCategoryService subCategoryService ) {
        this.categoryRepository = categoryRepository;
        this.categoryDTOMapper = categoryDTOMapper;
        this.subCategoryService = subCategoryService;
    }

    @Override
    public ResponseEntity<Object> createCategory(@NotNull final Category category) {
        final Category currentCategory = categoryRepository.save(category);
        final CategoryDTO categoryDTO = categoryDTOMapper.apply(currentCategory);
        return ResponseHandler.generateResponse(categoryDTO,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> updateCategory(final long categoryId, @NotNull final Category categoryDetails) {
        final Category currentCategory = getCategoryById(categoryId);
        currentCategory.setTitle(categoryDetails.getTitle());
        categoryDTOMapper.apply(categoryRepository.save(currentCategory));
        final String successResponse = String.format("The Category with ID : %d updated successfully.",categoryId);
        return ResponseHandler.generateResponse(successResponse,HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<Object> deleteCategoryById(final long categoryId) {
        final Category currentCategory = getCategoryById(categoryId);
        final List<SubCategory> subCategories = currentCategory.getSubCategories();

        if (subCategories.size() > 0) {
            subCategoryService.deleteSubCategoryAll(currentCategory.getSubCategories());
        }

        categoryRepository.deleteCategoriesById(categoryId);

        final String successResponse = String.format("The Category with ID : %d deleted successfully.",categoryId);
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> addSubCategory(final long categoryId, @NotNull final SubCategory subCategory) {
        final Category currentCategory =  getCategoryById(categoryId);
        subCategory.setCategory(currentCategory);
        currentCategory.getSubCategories().add(subCategory);
        categoryRepository.save(currentCategory);

        final String successResponse = String.format("The Sub category with TITLE : %s added successfully",subCategory.getTitle());
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> removeSubCategory(final long categoryId, final long subCategoryId) {
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

        final String successResponse = String.format("The Sub category with ID : %d deleted successfully",subCategoryId);
        return ResponseHandler.generateResponse(successResponse,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> fetchCategoryById(final long categoryId) {
        final Category currentCategory = getCategoryById(categoryId);
        final CategoryDTO category = categoryDTOMapper.apply(currentCategory);
        return ResponseHandler.generateResponse(category , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> fetchAllCategories() {
        final List<Category> currentCategories = categoryRepository.fetchAllCategory();
        final List<CategoryDTO> categories = currentCategories.stream().map(categoryDTOMapper).toList();
        return ResponseHandler.generateResponse(categories , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> fetchAllSubCategoryInCategoryById(final long categoryId) {
        final Category currentCategory = getCategoryById(categoryId);
        final List<SubCategoryDTO> subCategories = subCategoryService.mapToDTOList(currentCategory.getSubCategories());
        return ResponseHandler.generateResponse(subCategories , HttpStatus.OK);
    }

    @Override
    public Category getCategoryById(final long categoryId)
    {
        return categoryRepository.fetchCategoryById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("The Category with ID : %d could not be found in our system.", categoryId))
        );
    }
}
