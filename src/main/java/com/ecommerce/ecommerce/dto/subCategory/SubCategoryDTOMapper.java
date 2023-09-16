package com.ecommerce.ecommerce.dto.subCategory;

import com.ecommerce.ecommerce.model.subcategory.SubCategory;

import java.util.function.Function;

public class SubCategoryDTOMapper implements Function<SubCategory , SubCategoryDTO> {
    @Override
    public SubCategoryDTO apply(SubCategory subCategory) {
        return new SubCategoryDTO(
                subCategory.getId(),
                subCategory.getTitle()
        );
    }
}
