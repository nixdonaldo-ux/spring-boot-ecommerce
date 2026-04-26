package com.ecommerce.controller;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.entity.Order;
import com.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto, Authentication authentication) {
        // Set the user ID from the authenticated user
        Long userId = getUserIdFromAuthentication(authentication);
        orderDto.setUserId(userId);

        OrderDto createdOrder = orderService.convertToDto(orderService.createOrder(orderDto));
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<OrderDto>> getUserOrders(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = getUserIdFromAuthentication(authentication);
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderDto> orders = orderService.findOrdersByUser(userId, pageable)
                .map(orderService::convertToDto);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id, Authentication authentication) {
        return orderService.findById(id)
                .map(order -> {
                    // Check if user owns the order or is admin
                    if (isAdmin(authentication) || order.getUser().getId().equals(getUserIdFromAuthentication(authentication))) {
                        return ResponseEntity.ok(orderService.convertToDto(order));
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long id, @RequestParam Order.OrderStatus status) {
        OrderDto updatedOrder = orderService.convertToDto(orderService.updateOrderStatus(id, status));
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id, Authentication authentication) {
        Order order = orderService.findById(id).orElse(null);
        if (order != null && order.getUser().getId().equals(getUserIdFromAuthentication(authentication))) {
            orderService.cancelOrder(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderDto>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // This would need a method in OrderService to get all orders with pagination
        // For now, returning empty page
        return ResponseEntity.ok(Page.empty());
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        // This assumes UserPrincipal has getId method
        return ((com.ecommerce.security.UserPrincipal) authentication.getPrincipal()).getId();
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
}