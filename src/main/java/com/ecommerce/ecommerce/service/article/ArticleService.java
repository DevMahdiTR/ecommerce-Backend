package com.ecommerce.ecommerce.service.article;

import com.ecommerce.ecommerce.dto.article.ArticleDTO;
import com.ecommerce.ecommerce.model.article.Article;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    public ResponseEntity<Object> fetchArticleById(final long articleId);
    public ResponseEntity<Object> updateArticleById(final long articleId, @NotNull String articleJson) throws IOException;
    public ResponseEntity<Object> deleteArticleById(final long articleId) throws IOException;
    public ResponseEntity<Object> addImageToArticle(final long articleId , @NotNull final MultipartFile image) throws IOException;
    public ResponseEntity<Object> removeImageFromArticle(final long articleId , final long imageId) throws IOException;
    public ResponseEntity<byte[]>  fetchImageFromArticle(final long articleId, final int fileIndex) throws IOException;
    public ResponseEntity<Object> fetchAllArticle(final long pageNumber);
    public void deleteAllArticles (final List<Article> articles);
    public ArticleDTO mapToDTOItem(final Article article);
    public List<ArticleDTO> mapToDTOList(List<Article> articles);
    public Article getArticleById(final long articleId);

}
