package com.ecommerce.ecommerce.dto.order;

import com.ecommerce.ecommerce.model.order.SubOrder;

import java.util.List;
import java.util.UUID;

public record OrderDTO (
        long id,
        boolean delivered,
        String address,
        String paymentMethode,
        float price,
        List<SubOrderDTO> subOrders,
        UUID userId
) {
}
