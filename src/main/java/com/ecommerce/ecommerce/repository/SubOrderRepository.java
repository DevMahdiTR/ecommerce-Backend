package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.order.SubOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubOrderRepository extends JpaRepository<SubOrder , Integer> {

}
