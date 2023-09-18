package com.ecommerce.ecommerce.dto.category;

import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTOMapper;
import com.ecommerce.ecommerce.model.category.Category;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CategoryDTOMapper implements Function<Category,CategoryDTO > {
    @Override
    public CategoryDTO apply(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getTitle(),
                Optional.ofNullable(category.getSubCategories())
                        .map(subCategoriesList -> subCategoriesList.stream().map(new SubCategoryDTOMapper()).toList())
                        .orElse(Collections.emptyList())
                );
    }
}
