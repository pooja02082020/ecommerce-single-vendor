package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    // USER
    @PostMapping("/place/{userId}")
    public Order placeOrder(@PathVariable int userId) {
        return service.placeOrder(userId);
    }

    // USER
    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable int userId) {
        return service.getUserOrders(userId);
    }

    // ADMIN
    @PutMapping("/{orderId}/status")
    public Order updateStatus(@PathVariable int orderId,
                              @RequestParam String status) {
        return service.updateStatus(orderId, status);
    }
}
