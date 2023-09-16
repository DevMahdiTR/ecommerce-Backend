package com.ecommerce.ecommerce.dto.category;

import com.ecommerce.ecommerce.model.subcategory.SubCategory;

import java.util.List;

public record CategoryDTO (
        long categoryId,
        String title,
        List<SubCategory> subCategories
){

}
