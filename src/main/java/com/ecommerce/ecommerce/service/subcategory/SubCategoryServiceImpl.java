package com.ecommerce.ecommerce.service.subcategory;

import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTOMapper;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import com.ecommerce.ecommerce.repository.SubCategoryRepository;
import org.springframework.transaction.annotation.Transactional;

public class SubCategoryServiceImpl implements  SubCategoryService{

    final SubCategoryRepository subCategoryRepository;
    final SubCategoryDTOMapper subCategoryDTOMapper;
    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, SubCategoryDTOMapper subCategoryDTOMapper) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryDTOMapper = subCategoryDTOMapper;
    }

    @Override
    public SubCategory getSubCategoryById(final long subCategoryId) {
        return subCategoryRepository.fetchSubCategoryById(subCategoryId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("The Sub Category with ID : %d could not be found in our system.", subCategoryId))
        );
    }


    @Transactional
    @Override
    public void deleteSubCategoryById(long subCategoryId) {
        final SubCategory subCategory = getSubCategoryById(subCategoryId);
        subCategoryRepository.deleteSubCategoriesById(subCategoryId);
    }
}
