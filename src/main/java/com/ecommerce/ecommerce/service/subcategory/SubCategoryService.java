package com.ecommerce.ecommerce.service.subcategory;

import com.ecommerce.ecommerce.dto.article.ArticleDTO;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTO;
import com.ecommerce.ecommerce.model.article.Article;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SubCategoryService {

    public ResponseEntity<Object> updateSubCategory(final long subCategoryId , @NotNull final SubCategory subCategory);
    public ResponseEntity<Object> deleteSubCategory(final long subCategoryId);
    public ResponseEntity<Object> fetchSubCategoryById(final long subCategoryId);
    public ResponseEntity<Object> fetchAllSubCategory();
    public ResponseEntity<Object> fetchArticleFromSubCategory(final long subCategoryId , final long pageNumber);
    public ResponseEntity<Object> addArticleToSubCategoryById(
            long subCategoryId ,
            final @NotNull List<MultipartFile> multipartFiles,
            @NotNull final String articleJson
    ) throws IOException;




    public SubCategoryDTO mapToDTOItem(final SubCategory subCategory);
    public List<SubCategoryDTO> mapToDTOList(final List<SubCategory> subCategories);
    public SubCategory getSubCategoryById(final long subCategoryId);
    public void deleteSubCategoryById(final long subCategoryId);
    public void deleteSubCategoryAll(List<SubCategory> subCategories);

}
