package com.ecommerce.ecommerce.service.article;

import com.ecommerce.ecommerce.dto.article.ArticleDTO;
import com.ecommerce.ecommerce.dto.article.ArticleDTOMapper;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.article.Article;
import com.ecommerce.ecommerce.model.file.FileData;
import com.ecommerce.ecommerce.repository.ArticleRepository;
import com.ecommerce.ecommerce.service.chapter.ChapterService;
import com.ecommerce.ecommerce.service.detail.DetailService;
import com.ecommerce.ecommerce.service.file.FileService;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import com.ecommerce.ecommerce.utility.CustomResponseList;
import com.ecommerce.ecommerce.utility.responses.ResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;
    private final ArticleDTOMapper articleDTOMapper;
    private final FileService fileService;
    private final DetailService detailService;
    private final ChapterService chapterService;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleDTOMapper articleDTOMapper, FileService fileService, DetailService detailService, ChapterService chapterService) {
        this.articleRepository = articleRepository;
        this.articleDTOMapper = articleDTOMapper;
        this.fileService = fileService;
        this.detailService = detailService;
        this.chapterService = chapterService;
    }


    @Override
    public ResponseEntity<Object> fetchArticleById(final long articleId) {
        final Article currentArticle = getArticleById(articleId);
        final ArticleDTO article = articleDTOMapper.apply(currentArticle);
        return ResponseHandler.generateResponse(article , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateArticleById(final long articleId, List<MultipartFile> multipartFiles, @NotNull String articleJson) throws IOException {

        final Article existingArticle  = getArticleById(articleId);
        final Article updatedArticle = new ObjectMapper().readValue(articleJson , Article.class);
        final List<FileData> prevImages = existingArticle.getFiles();

        existingArticle.setTitle(updatedArticle.getTitle());
        existingArticle.setPrice(updatedArticle.getPrice());
        existingArticle.setQuantity(updatedArticle.getQuantity());
        existingArticle.setReference(updatedArticle.getReference());
        existingArticle.setLayoutDescription(updatedArticle.getLayoutDescription());

        existingArticle.getChapters().clear();
        existingArticle.getChapters().addAll(updatedArticle.getChapters());
        existingArticle.getDetails().clear();
        existingArticle.getDetails().addAll(updatedArticle.getDetails());

        if(multipartFiles != null && !multipartFiles.isEmpty())
        {
            existingArticle.getFiles().clear();
            fileService.deleteAllFiles(prevImages);
            List<FileData> newImages = new ArrayList<>();
            for(MultipartFile multipartFile : multipartFiles)
            {
                FileData image = fileService.processUploadedFile(multipartFile);
                image.setArticle(existingArticle);
                newImages.add(image);
            }
            existingArticle.getFiles().addAll(newImages);
        }

        articleRepository.save(existingArticle);


        final String successResponse = String.format("Article with ID %d updated successfully", articleId);
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteArticleById(long articleId) throws IOException {
        final Article existingArticle = getArticleById(articleId);

        chapterService.deleteAllChapters(existingArticle.getChapters());
        detailService.deleteAllDetails(existingArticle.getDetails());
        fileService.deleteAllFiles(existingArticle.getFiles());
        articleRepository.deleteArticleById(articleId);

        final String successResponse = String.format("Article with ID %d deleted successfully", articleId);
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> addImageToArticle(long articleId, @NotNull MultipartFile image) throws IOException {
        final Article existingArticle =  getArticleById(articleId);
        final FileData newImage = fileService.processUploadedFile(image);
        newImage.setArticle(existingArticle);
        articleRepository.save(existingArticle);

        final String successResponse ="The image is added successfully.";
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> removeImageFromArticle(long articleId, long imageId) throws IOException {
        final Article exisitingArticle = getArticleById(articleId);
        final FileData existingImage = fileService.getFileDataById(imageId);

        if(!exisitingArticle.getFiles().contains(existingImage))
        {
            throw new IllegalStateException(String.format("The Image with ID : %d  does not belong to this article", imageId));
        }

        exisitingArticle.getFiles().remove(existingImage);
        existingImage.setArticle(null);
        fileService.deleteFileFromFileSystem(existingImage);
        articleRepository.save(exisitingArticle);
        final String successResponse = String.format("The image with ID : %d deleted successfully",imageId);
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }
    @Override
    public  ResponseEntity<byte[]> fetchImageFromArticle(final long articleId,final int fileIndex) throws IOException {

        final Article article = getArticleById(articleId);
        if(fileIndex >= article.getFiles().size())
        {
            throw new IllegalStateException("The file index is out of range.");
        }
        final FileData fileData = article.getFiles().get(fileIndex);
        return fileService.downloadFile(fileData);
    }


    @Override
    public ResponseEntity<Object> fetchAllArticle(final long pageNumber) {
        final Pageable pageable = PageRequest.of((int) pageNumber - 1, 10);

        final List<ArticleDTO> articles = articleRepository.fetchAllArticles(pageable).stream().map(articleDTOMapper).toList();
        if(articles.isEmpty() && pageNumber > 1)
        {
            return fetchAllArticle(1);
        }
        final long total  = articleRepository.getTotalArticleCount();
        return ResponseHandler.generateResponse(articles , HttpStatus.OK , articles.size() , total);
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

    public Article getArticleById(final long articleId)
    {
        return articleRepository.fetchArticleById(articleId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("The Article with ID : %d could not be found in our system", articleId))
        );
    }
}
