package com.ecommerce.ecommerce.service.order;

import com.ecommerce.ecommerce.model.order.Order;
import com.ecommerce.ecommerce.utility.responses.ResponseHandler;
import org.aspectj.weaver.ast.Or;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface OrderService {

    public ResponseEntity<Object> fetchOrderById(final long orderId);
    public ResponseEntity<Object> fetchAllOrders(final long pageNumber);
    public ResponseEntity<Object> fetchOrdersOfUser(final UUID userId, final long pageNumber);
    public  ResponseEntity<Object> placeOrder(@NotNull UserDetails userDetails,@NotNull final String orderJson);
    public ResponseEntity<Object> finishOrder(final long orderId);
    public Order getOrderById(final long orderId);
}
