package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.chapter.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ChapterRepository extends JpaRepository<Chapter , Integer> {



    @Transactional
    @Modifying
    @Query(value = "delete from Chapter c where c in :chapters")
    public void deleteAllChapters(@Param("chapters") final List<Chapter> chapters);
}
