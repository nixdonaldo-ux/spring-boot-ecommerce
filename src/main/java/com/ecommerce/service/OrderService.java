package com.ecommerce.service;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order createOrder(OrderDto orderDto);

    Order updateOrderStatus(Long id, Order.OrderStatus status);

    void cancelOrder(Long id);

    Optional<Order> findById(Long id);

    Optional<Order> findByOrderNumber(String orderNumber);

    Page<Order> findOrdersByUser(Long userId, Pageable pageable);

    List<Order> findOrdersByStatus(Order.OrderStatus status);

    List<Order> findOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<Order> findOrdersByUserAndStatus(Long userId, Order.OrderStatus status);

    OrderDto convertToDto(Order order);

    List<OrderDto> convertToDtoList(List<Order> orders);
}