package com.ecommerce.ecommerce.service.article;

import com.ecommerce.ecommerce.dto.article.ArticleDTO;
import com.ecommerce.ecommerce.model.article.Article;

import java.util.List;

public interface ArticleService {


    public Article save(final Article article);
    public void deleteAllArticles (final List<Article> articles);
    public ArticleDTO mapToDTOItem(final Article article);
    public List<ArticleDTO> mapToDTOList(List<Article> articles);

}
