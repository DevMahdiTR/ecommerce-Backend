package com.ecommerce.ecommerce.service.article;

import com.ecommerce.ecommerce.dto.article.ArticleDTO;
import com.ecommerce.ecommerce.dto.article.ArticleDTOMapper;
import com.ecommerce.ecommerce.model.article.Article;
import com.ecommerce.ecommerce.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;
    private final ArticleDTOMapper articleDTOMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleDTOMapper articleDTOMapper) {
        this.articleRepository = articleRepository;
        this.articleDTOMapper = articleDTOMapper;
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteAllArticles(List<Article> articles) {
        articleRepository.deleteAllArticles(articles);
    }

    @Override
    public ArticleDTO mapToDTOItem(Article article) {
        return articleDTOMapper.apply(article);
    }

    @Override
    public List<ArticleDTO> mapToDTOList(List<Article> articles)
    {
        return articles.stream().map(articleDTOMapper).toList();
    }
}
