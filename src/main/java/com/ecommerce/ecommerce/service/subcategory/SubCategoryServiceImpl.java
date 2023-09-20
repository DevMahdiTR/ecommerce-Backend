package com.ecommerce.ecommerce.service.subcategory;

import com.ecommerce.ecommerce.dto.article.ArticleDTO;
import com.ecommerce.ecommerce.dto.article.ArticleDTOMapper;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTO;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTOMapper;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.article.Article;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import com.ecommerce.ecommerce.repository.SubCategoryRepository;
import com.ecommerce.ecommerce.service.article.ArticleService;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubCategoryServiceImpl implements  SubCategoryService{

    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryDTOMapper subCategoryDTOMapper;
    private final ArticleService articleService;
    private final ArticleDTOMapper articleDTOMapper;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, SubCategoryDTOMapper subCategoryDTOMapper, ArticleService articleService, ArticleDTOMapper articleDTOMapper) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryDTOMapper = subCategoryDTOMapper;
        this.articleService = articleService;
        this.articleDTOMapper = articleDTOMapper;
    }

    @Override
    public CustomResponseEntity<SubCategoryDTO> createSubCategory(@NotNull SubCategory subCategory) {
        final SubCategory currentSubCategory = subCategoryRepository.save(subCategory);
        final SubCategoryDTO subCategoryDTO = subCategoryDTOMapper.apply(currentSubCategory);
        return new CustomResponseEntity<>(HttpStatus.OK , subCategoryDTO);
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
    public CustomResponseEntity<List<SubCategoryDTO>> fetchAllSubCategory() {
        final List<SubCategory> currentSubCategories = subCategoryRepository.fetchAllSubCategories();
        final List<SubCategoryDTO> subCategories = currentSubCategories.stream().map(subCategoryDTOMapper).toList();

        return new CustomResponseEntity<>(HttpStatus.OK, subCategories);
    }

    @Override
    public CustomResponseEntity<List<ArticleDTO>> fetchArticleFromSubCategory(long subCategoryId) {
        final SubCategory currentSubCategory = getSubCategoryById(subCategoryId);
        final List<Article> currentArticles = currentSubCategory.getArticles();
        final List<ArticleDTO> articles = currentArticles.stream().map(articleDTOMapper).toList();

        return new CustomResponseEntity<>(HttpStatus.OK , articles);
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
