package com.ecommerce.ecommerce.controller.subcategory;

import com.ecommerce.ecommerce.dto.article.ArticleDTO;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTO;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import com.ecommerce.ecommerce.service.subcategory.SubCategoryService;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PutMapping("/{subcategoryId}")
    public CustomResponseEntity<String> updateSubCategory(
            @PathVariable("subcategoryId") final long subcategoryId,
            @NotNull @Valid @RequestBody SubCategory subCategory)
    {
        return subCategoryService.updateSubCategory(subcategoryId , subCategory);
    }

    @DeleteMapping("/{subcategoryId}")
    public CustomResponseEntity<String> deleteSubCategory(@PathVariable("subcategoryId") final long subcategoryId)
    {
        return subCategoryService.deleteSubCategory(subcategoryId);
    }

    @GetMapping()
    public CustomResponseEntity<List<SubCategoryDTO>> fetchAllSubCategory()
    {
        return subCategoryService.fetchAllSubCategory();
    }

    @GetMapping("/{subcategoryId}")
    public CustomResponseEntity<SubCategoryDTO> fetchSubCategoryById(@PathVariable("subcategoryId") final long subcategoryId)
    {
        return subCategoryService.fetchSubCategoryById(subcategoryId);
    }

    @GetMapping("/{subcategoryId}/articles")
    public CustomResponseEntity<List<ArticleDTO>> fetchArticleFromSubCategory(@PathVariable("subcategoryId") final long subcategoryId)
    {
        return subCategoryService.fetchArticleFromSubCategory(subcategoryId);
    }

    @PutMapping("/{subcategoryId}/articles")
    public CustomResponseEntity<String> addArticleToSubCategory(
            @PathVariable("subcategoryId") final long subcategoryId,
            @RequestParam("images") List<MultipartFile> multipartFiles,
            @RequestParam(value = "articleJson" , required = true) final String articleJson
    ) throws IOException {
        return subCategoryService.addArticleToSubCategoryById(subcategoryId,multipartFiles,articleJson);
    }

}
