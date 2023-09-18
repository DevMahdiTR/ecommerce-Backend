package com.ecommerce.ecommerce.service.subcategory;

import com.ecommerce.ecommerce.model.subcategory.SubCategory;

import java.util.List;

public interface SubCategoryService {

    public SubCategory getSubCategoryById(final long subCategoryId);
    public void deleteSubCategoryById(final long subCategoryId);
    public void deleteSubCategoryAll(List<SubCategory> subCategories);

}
