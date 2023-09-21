package com.ecommerce.ecommerce.dto.article;

import com.ecommerce.ecommerce.dto.chapter.ChapterDTOMapper;
import com.ecommerce.ecommerce.dto.detail.DetailDTOMapper;
import com.ecommerce.ecommerce.dto.file.FileDataDTOMapper;
import com.ecommerce.ecommerce.model.article.Article;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ArticleDTOMapper implements Function<Article ,ArticleDTO> {
    @Override
    public ArticleDTO apply(Article article) {
        return new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getPrice(),
                article.getQuantity(),
                article.getLayoutDescription(),
                article.getReference(),
                article.getChapters().stream().map(new ChapterDTOMapper()).toList(),
                article.getDetails().stream().map(new DetailDTOMapper()).toList(),
                article.getFiles().stream().map(new FileDataDTOMapper()).toList()
        );
    }
}
