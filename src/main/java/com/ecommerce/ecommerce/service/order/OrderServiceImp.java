package com.ecommerce.ecommerce.service.order;

import com.ecommerce.ecommerce.dto.order.OrderDTO;
import com.ecommerce.ecommerce.dto.order.OrderDTOMapper;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.order.Order;
import com.ecommerce.ecommerce.repository.OrderRepository;
import com.ecommerce.ecommerce.utility.responses.ResponseHandler;
import org.aspectj.weaver.ast.Or;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public class OrderServiceImp  implements  OrderService{


    private final OrderRepository orderRepository;
    private final OrderDTOMapper orderDTOMapper;

    public OrderServiceImp(OrderRepository orderRepository, OrderDTOMapper orderDTOMapper) {
        this.orderRepository = orderRepository;
        this.orderDTOMapper = orderDTOMapper;
    }

    @Override
    public ResponseEntity<Object> fetchOrderById(long orderId) {
        final Order existingOrder = getOrderById(orderId);
        final OrderDTO order = orderDTOMapper.apply(existingOrder);

        return ResponseHandler.generateResponse(order , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> fetchAllOrders(long pageNumber) {
        final Pageable pageable  = PageRequest.of((int)pageNumber - 1, 10 );
        final List<OrderDTO> orders = orderRepository.fetchAllOrders(pageable).stream().map(orderDTOMapper).toList();
        final long total = orderRepository.getTotalOrderCount();

        return ResponseHandler.generateResponse(orders,HttpStatus.OK,orders.size(),total);

    }

    @Override
    public ResponseEntity<Object> fetchOrdersOfUser(final UUID userId, final long pageNumber) {
        final Pageable pageable = PageRequest.of((int) pageNumber-1 , 10);
        final List<OrderDTO> orders = orderRepository.fetchOrdersFromUser(userId , pageable).stream().map(orderDTOMapper).toList();
        final long total = orderRepository.getTotalOrderCountByUser(userId);

        return ResponseHandler.generateResponse(orders , HttpStatus.OK,orders.size() , total);
    }

    @Override
    public ResponseEntity<Object> placeOrder(@NotNull UserDetails userDetails, @NotNull String orderJson) {
        return null;
    }

    @Override
    public ResponseEntity<Object> finishOrder(final long orderId) {
        final Order existingOrder = getOrderById(orderId);
        existingOrder.setDelivered(true);
        orderRepository.save(existingOrder);

        final String successResponse = String.format("The Order with ID : %d delivered successfully." , orderId);
        return ResponseHandler.generateResponse(successResponse, HttpStatus.OK);
    }

    @Override
    public Order getOrderById(final long orderId)
    {
        return orderRepository.fetchOrderById(orderId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("The Order with ID : %d could not be found in our system." , orderId))
        );
    }
}
