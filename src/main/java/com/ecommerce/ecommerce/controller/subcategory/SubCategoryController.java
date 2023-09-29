package com.ecommerce.ecommerce.controller.subcategory;

import com.ecommerce.ecommerce.dto.article.ArticleDTO;
import com.ecommerce.ecommerce.dto.subCategory.SubCategoryDTO;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import com.ecommerce.ecommerce.service.subcategory.SubCategoryService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> updateSubCategory(
            @PathVariable("subcategoryId") final long subcategoryId,
            @NotNull @Valid @RequestBody SubCategory subCategory)
    {
        return subCategoryService.updateSubCategory(subcategoryId , subCategory);
    }

    @DeleteMapping("/{subcategoryId}")
    public ResponseEntity<Object> deleteSubCategory(@PathVariable("subcategoryId") final long subcategoryId)
    {
        return subCategoryService.deleteSubCategory(subcategoryId);
    }

    @GetMapping()
    public ResponseEntity<Object> fetchAllSubCategory()
    {
        return subCategoryService.fetchAllSubCategory();
    }

    @GetMapping("/{subcategoryId}")
    public ResponseEntity<Object> fetchSubCategoryById(@PathVariable("subcategoryId") final long subcategoryId)
    {
        return subCategoryService.fetchSubCategoryById(subcategoryId);
    }

    @GetMapping("/{subcategoryId}/articles")
    public ResponseEntity<Object> fetchArticleFromSubCategory(
            @PathVariable("subcategoryId") final long subcategoryId,
            @RequestParam(value = "pageNumber" ,required = true) final long pageNumber
        )
    {
        return subCategoryService.fetchArticleFromSubCategory(subcategoryId , pageNumber);
    }

    @PutMapping("/{subcategoryId}/articles")
    public ResponseEntity<Object>  addArticleToSubCategory(
            @PathVariable("subcategoryId") final long subcategoryId,
            @RequestParam("images") List<MultipartFile> multipartFiles,
            @RequestParam(value = "articleJson" , required = true) final String articleJson
    ) throws IOException {
        return subCategoryService.addArticleToSubCategoryById(subcategoryId,multipartFiles,articleJson);
    }

}
