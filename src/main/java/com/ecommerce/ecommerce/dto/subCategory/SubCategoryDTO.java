package com.ecommerce.ecommerce.dto.subCategory;

import com.ecommerce.ecommerce.dto.article.ArticleDTO;

import java.util.List;

public record SubCategoryDTO (
        long id,
        String title,
        List<ArticleDTO> articles
){
}
