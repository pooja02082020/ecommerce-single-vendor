package com.example.ecommerce.controller;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.OrderService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;
	private final UserRepository userRepo;

	public OrderController(OrderService orderService, UserRepository userRepo) {
		this.orderService = orderService;
		this.userRepo = userRepo;
	}

	private User getUser(Authentication auth) {
		return userRepo.findByEmail(auth.getName()).orElseThrow();
	}

	@PostMapping
	public Order placeOrder(Authentication auth) {
		return orderService.placeOrder(getUser(auth));
	}

	@PostMapping("/{orderId}/pay")
	public Order pay(@PathVariable Long orderId) {
		return orderService.simulatePayment(orderId);
	}

	@GetMapping("/my")
	public List<Order> myOrders(Authentication auth) {
		return orderService.myOrders(getUser(auth));
	}

	@GetMapping("/{id}")
	public Order getOrder(@PathVariable Long id) {
		return orderService.getOrder(id);
	}
}
