package com.ecommerce.ecommerce.controller.article;
import com.ecommerce.ecommerce.service.article.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/articles")
public class ArticleController {

    private final ArticleService articleService ;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Object> fetchArticleById(@PathVariable("articleId") final long articleId)
    {
        return articleService.fetchArticleById(articleId);
    }
    @PutMapping("/{articleId}")
    public  ResponseEntity<Object> updateArticleById(
            @PathVariable("articleId") final long articleId,
            @RequestParam(value = "images", required = false) List<MultipartFile> multipartFiles,
            @RequestParam(value = "articleJson", required = true) final String articleJson
    ) throws IOException {
        return articleService.updateArticleById(articleId, multipartFiles , articleJson);
    }
    @PutMapping("/{articleId}/images")
    public ResponseEntity<Object> addImageToArticle(@PathVariable("articleId") final long articleId , @RequestParam(value = "image" , required = true) final MultipartFile multipartFile) throws IOException {
        return articleService.addImageToArticle(articleId, multipartFile);
    }
    @DeleteMapping("/{articleId}/images/{imageId}")
    public ResponseEntity<Object> removeImageFromArticle(@PathVariable("articleId") final long articleId , @PathVariable("imageId") final long imageId) throws IOException {
        return articleService.removeImageFromArticle(articleId, imageId);
    }
    @DeleteMapping("/{articleId}")
    public ResponseEntity<Object> deleteArticleById(@PathVariable("articleId") final long articleId) throws IOException {
        return articleService.deleteArticleById(articleId);
    }
    @GetMapping()
    public  ResponseEntity<Object> fetchAllArticles(@RequestParam("pageNumber") final long pageNumber)
    {
        return articleService.fetchAllArticle(pageNumber);
    }
    @GetMapping("/{articleId}/images/{fileIndex}")
    public ResponseEntity<byte[]>  fetchImageFromArticle(@PathVariable("articleId") final long articleId ,@PathVariable("fileIndex") final int fileIndex) throws IOException {
        return articleService.fetchImageFromArticle(articleId , fileIndex);
    }

}
