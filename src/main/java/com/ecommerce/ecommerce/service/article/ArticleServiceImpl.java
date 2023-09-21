package com.ecommerce.ecommerce.service.article;

import com.ecommerce.ecommerce.model.article.Article;
import com.ecommerce.ecommerce.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteAllArticles(List<Article> articles) {
        articleRepository.deleteAllArticles(articles);
    }
}
