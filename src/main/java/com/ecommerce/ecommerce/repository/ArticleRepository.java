package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.article.Article;
import com.ecommerce.ecommerce.model.subcategory.SubCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface ArticleRepository extends JpaRepository<Article , Integer> {


    @Query(value = "select a from Article a where a.id = :articleId")
    Optional<Article> fetchArticleById(@Param("articleId") final long articleId);

    @Query(value = "select a from Article a order by a.id")
    List<Article> fetchAllArticles(Pageable pageable);

    @Query(value = "select count(a) from Article a")
    long getTotalArticleCount();

    @Modifying
    @Transactional
    @Query(value = "delete from Article  a where a.id = :articleId")
    void deleteArticleById(@Param("articleId") final long articleId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Article a WHERE a IN :articles")
    void deleteAllArticles(@Param("articles") List<Article> articles);


}
