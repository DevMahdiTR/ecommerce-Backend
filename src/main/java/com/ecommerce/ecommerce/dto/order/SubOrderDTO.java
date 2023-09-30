package com.ecommerce.ecommerce.dto.order;

public record SubOrderDTO(
        long id,
        int quantity,
        long articleId,
        long orderId
) {
}
