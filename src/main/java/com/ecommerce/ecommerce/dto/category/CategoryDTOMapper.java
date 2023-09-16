package com.ecommerce.ecommerce.dto.category;

import com.ecommerce.ecommerce.model.category.Category;

import java.util.function.Function;

public class CategoryDTOMapper implements Function<Category,CategoryDTO > {
    @Override
    public CategoryDTO apply(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getTitle(),
                category.getSubCategories()
        );
    }
}
