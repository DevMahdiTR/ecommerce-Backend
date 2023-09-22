package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.detail.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DetailRepository extends JpaRepository<Detail ,Integer> {


    @Transactional
    @Modifying
    @Query(value = "delete from Detail d where d in :details")
    public void deleteAllDetails(@Param("details") final List<Detail> details);
}
