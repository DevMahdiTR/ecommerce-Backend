package com.ecommerce.ecommerce.dto.order;

import com.ecommerce.ecommerce.model.order.SubOrder;

import java.util.function.Function;

public class SubOrderDTOMapper  implements Function<SubOrder , SubOrderDTO> {
    @Override
    public SubOrderDTO apply(SubOrder subOrder) {
        return new SubOrderDTO(
                subOrder.getId(),
                subOrder.getQuantity(),
                subOrder.getArticleId(),
                subOrder.getOrder().getId()
        );
    }
}
