package com.ecommerce.ecommerce.dto.subCategory;

import com.ecommerce.ecommerce.dto.article.ArticleDTOMapper;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SubCategoryDTOMapper implements Function<SubCategory , SubCategoryDTO> {
    @Override
    public SubCategoryDTO apply(SubCategory subCategory) {
        return new SubCategoryDTO(
                subCategory.getId(),
                subCategory.getTitle(),
                subCategory.getArticles().stream().map(new ArticleDTOMapper()).toList()
        );
    }
}
