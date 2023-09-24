package com.ecommerce.ecommerce.service.article;

import com.ecommerce.ecommerce.dto.article.ArticleDTO;
import com.ecommerce.ecommerce.model.article.Article;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import com.ecommerce.ecommerce.utility.CustomResponseList;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    public CustomResponseEntity<ArticleDTO> fetchArticleById(final long articleId);
    public CustomResponseEntity<String> updateArticleById(final long articleId, List<MultipartFile> multipartFiles, @NotNull String articleJson) throws IOException;
    public CustomResponseEntity<String> deleteArticleById(final long articleId) throws IOException;
    public CustomResponseEntity<String> addImageToArticle(final long articleId , @NotNull final MultipartFile image);
    public CustomResponseEntity<String> removeImageFromArticle(final long articleId , final long imageId);
    public CustomResponseEntity<byte[]> downloadImageFromArticle(final long articleId) throws IOException;
    public CustomResponseList<ArticleDTO> fetchAllArticle(final long pageNumber);
    public void deleteAllArticles (final List<Article> articles);
    public ArticleDTO mapToDTOItem(final Article article);
    public List<ArticleDTO> mapToDTOList(List<Article> articles);

}
