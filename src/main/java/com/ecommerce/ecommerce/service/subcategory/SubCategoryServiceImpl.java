package com.ecommerce.ecommerce.service.subcategory;

import com.ecommerce.ecommerce.dto.article.ArticleDTO;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTO;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTOMapper;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.article.Article;
import com.ecommerce.ecommerce.model.file.FileData;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import com.ecommerce.ecommerce.repository.SubCategoryRepository;
import com.ecommerce.ecommerce.service.article.ArticleService;
import com.ecommerce.ecommerce.service.file.FileService;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoryServiceImpl implements  SubCategoryService{

    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryDTOMapper subCategoryDTOMapper;
    private final ArticleService articleService;

    private final FileService fileService;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, SubCategoryDTOMapper subCategoryDTOMapper, ArticleService articleService , FileService fileService) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryDTOMapper = subCategoryDTOMapper;
        this.articleService = articleService;

        this.fileService = fileService;
    }

    @Override
    public CustomResponseEntity<String> updateSubCategory(long subCategoryId, @NotNull SubCategory subCategory) {
        final SubCategory currentSubCategory = getSubCategoryById(subCategoryId);
        currentSubCategory.setTitle(subCategory.getTitle());
        subCategoryRepository.save(currentSubCategory);

        final String successResponse = String.format("The Sub category with ID : %d updated successfully", subCategoryId);
        return new CustomResponseEntity<>(HttpStatus.OK , successResponse);
    }

    @Transactional
    @Override
    public CustomResponseEntity<String> deleteSubCategory(long subCategoryId) {
        final SubCategory currentSubCategory = getSubCategoryById(subCategoryId);
        final List<Article> articles = currentSubCategory.getArticles();

        if(articles.size() > 0)
        {
            articleService.deleteAllArticles(articles);
        }

        deleteSubCategoryById(currentSubCategory.getId());

        final String successResponse = String.format("The Sub Category with ID : %d deleted successfully.",subCategoryId);
        return new CustomResponseEntity<>(HttpStatus.OK, successResponse);

    }

    @Override
    public CustomResponseEntity<SubCategoryDTO> fetchSubCategoryById(long subCategoryId) {
        final SubCategory currentSubCategory = getSubCategoryById(subCategoryId);
        final SubCategoryDTO subCategory = subCategoryDTOMapper.apply(currentSubCategory);
        return new CustomResponseEntity<>(HttpStatus.OK , subCategory);
    }

    @Override
    public CustomResponseEntity<List<SubCategoryDTO>> fetchAllSubCategory() {
        final List<SubCategory> currentSubCategories = subCategoryRepository.fetchAllSubCategories();
        final List<SubCategoryDTO> subCategories = currentSubCategories.stream().map(subCategoryDTOMapper).toList();

        return new CustomResponseEntity<>(HttpStatus.OK, subCategories);
    }

    @Override
    public CustomResponseEntity<List<ArticleDTO>> fetchArticleFromSubCategory(long subCategoryId) {
        final SubCategory currentSubCategory = getSubCategoryById(subCategoryId);
        final List<Article> currentArticles = currentSubCategory.getArticles();
        final List<ArticleDTO> articles = articleService.mapToDTOList(currentArticles);

        return new CustomResponseEntity<>(HttpStatus.OK , articles);
    }

    @Override
    public CustomResponseEntity<String> addArticleToSubCategoryById(long subCategoryId, @NotNull List<MultipartFile> multipartFiles, @NotNull String articleJson) throws IOException {


        final SubCategory currentSubCategory = getSubCategoryById(subCategoryId);
        final Article article = new ObjectMapper().readValue(articleJson , Article.class);

        for(var chapter : article.getChapters())
        {
            chapter.setArticle(article);
        }
        for(var detail : article.getDetails())
        {
            detail.setArticle(article);
        }
        article.setSubCategory(currentSubCategory);

        final List<FileData> images  = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            FileData image = fileService.processUploadedFile(multipartFile);
            image.setArticle(article);
            images.add(image);
        }
        article.setFiles(images);
        currentSubCategory.getArticles().add(article);
        subCategoryRepository.save(currentSubCategory);

        final String successResponse = String.format("The Article with TITLE : %s added successfully",article.getTitle());
        return new CustomResponseEntity<>(HttpStatus.OK , successResponse);
    }

    @Override
    public SubCategoryDTO mapToDTOItem(SubCategory subCategory) {
        return subCategoryDTOMapper.apply(subCategory);
    }

    @Override
    public List<SubCategoryDTO> mapToDTOList(List<SubCategory> subCategories) {
        return subCategories.stream().map(subCategoryDTOMapper).toList();
    }

    @Override
    public SubCategory getSubCategoryById(final long subCategoryId) {
        return subCategoryRepository.fetchSubCategoryById(subCategoryId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("The Sub Category with ID : %d could not be found in our system.", subCategoryId))
        );
    }

    @Transactional
    @Override
    public void deleteSubCategoryById(long subCategoryId) {
        final SubCategory subCategory = getSubCategoryById(subCategoryId);
        subCategoryRepository.deleteSubCategorieById(subCategoryId);
    }

    @Transactional
    @Override
    public void deleteSubCategoryAll(List<SubCategory> subCategories)
    {
        subCategoryRepository.deleteAllSubCategories(subCategories);
    }
}
