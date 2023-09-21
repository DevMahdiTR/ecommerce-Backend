package com.ecommerce.ecommerce.service.subcategory;

import com.ecommerce.ecommerce.dto.article.ArticleDTO;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTO;
import com.ecommerce.ecommerce.model.article.Article;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SubCategoryService {

    public CustomResponseEntity<String> updateSubCategory(final long subCategoryId , @NotNull final SubCategory subCategory);
    public CustomResponseEntity<String> deleteSubCategory(final long subCategoryId);
    public CustomResponseEntity<SubCategoryDTO> fetchSubCategoryById(final long subCategoryId);
    public CustomResponseEntity<List<SubCategoryDTO>> fetchAllSubCategory();
    public CustomResponseEntity<List<ArticleDTO>> fetchArticleFromSubCategory(final long subCategoryId);
    public CustomResponseEntity<ArticleDTO> addArticleToSubCategoryById(
            long subCategoryId ,
            final @NotNull List<MultipartFile> multipartFiles,
            @NotNull final String articleJson
    ) throws IOException;


    public SubCategory getSubCategoryById(final long subCategoryId);
    public void deleteSubCategoryById(final long subCategoryId);
    public void deleteSubCategoryAll(List<SubCategory> subCategories);

}
