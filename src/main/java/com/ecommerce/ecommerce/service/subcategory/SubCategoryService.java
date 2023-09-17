package com.ecommerce.ecommerce.service.subcategory;

import com.ecommerce.ecommerce.model.subcategory.SubCategory;

public interface SubCategoryService {

    public SubCategory getSubCategoryById(final long subCategoryId);


    public void deleteSubCategoryById(final long subCategoryId);
}
