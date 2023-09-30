package com.ecommerce.ecommerce.dto.order;

import com.ecommerce.ecommerce.model.order.Order;

import java.util.function.Function;

public class OrderDTOMapper implements Function<Order , OrderDTO> {
    @Override
    public OrderDTO apply(Order order) {
        return new OrderDTO(
                order.getId(),
                order.isDelivered(),
                order.getAddress(),
                order.getPaymentMethode(),
                order.getPrice(),
                order.getSubOrders().stream().map(new SubOrderDTOMapper()).toList(),
                order.getUser().getId()
        );
    }
}
