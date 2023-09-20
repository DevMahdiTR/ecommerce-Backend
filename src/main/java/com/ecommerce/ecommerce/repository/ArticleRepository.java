package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.article.Article;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article , Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Article a WHERE a IN :articles")
    void deleteAllArticles(@Param("articles") List<Article> articles);


}
