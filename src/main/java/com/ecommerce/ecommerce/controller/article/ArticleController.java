package com.ecommerce.ecommerce.controller.article;


import com.ecommerce.ecommerce.dto.article.ArticleDTO;
import com.ecommerce.ecommerce.service.article.ArticleService;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import com.ecommerce.ecommerce.utility.CustomResponseList;
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
    public CustomResponseEntity<ArticleDTO> fetchArticleById(@PathVariable("articleId") final long articleId)
    {
        return articleService.fetchArticleById(articleId);
    }

    @PutMapping("/{articleId}")
    public CustomResponseEntity<String> updateArticleById(
            @PathVariable("articleId") final long articleId,
            @RequestParam(value = "images", required = false) List<MultipartFile> multipartFiles,
            @RequestParam(value = "articleJson", required = true) final String articleJson
    ) throws IOException {
        return articleService.updateArticleById(articleId, multipartFiles , articleJson);
    }

    @DeleteMapping("/{articleId}")
    public CustomResponseEntity<String> deleteArticleById(@PathVariable("articleId") final long articleId) throws IOException {
        return articleService.deleteArticleById(articleId);
    }

    @GetMapping()
    public CustomResponseList<ArticleDTO> fetchAllArticles(@RequestParam("pageNumber") final long pageNumber)
    {
        return articleService.fetchAllArticle(pageNumber);
    }


}
