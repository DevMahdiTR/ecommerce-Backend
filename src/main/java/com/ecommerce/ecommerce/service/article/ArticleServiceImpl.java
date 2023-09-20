package com.ecommerce.ecommerce.service.article;

import com.ecommerce.ecommerce.model.article.Article;
import com.ecommerce.ecommerce.repository.ArticleRepository;

import java.util.List;

public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void deleteAllArticles(List<Article> articles) {
        articleRepository.deleteAllArticles(articles);
    }
}
