package com.example.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepo;
	private final CartRepository cartRepo;

	public OrderService(OrderRepository orderRepo, CartRepository cartRepo) {
		this.orderRepo = orderRepo;
		this.cartRepo = cartRepo;
	}

	public Order placeOrder(int userId) {

		Cart cart = cartRepo.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

		Order order = new Order();
		order.setUser(cart.getUser());
		order.setTotalAmount(cart.getTotalPrice());
		order.setPaymentStatus("PAID");
		order.setOrderStatus("PENDING");
		order.setCreatedAt(LocalDateTime.now());

		return orderRepo.save(order);
	}

	public List<Order> getUserOrders(int userId) {
		return orderRepo.findByUserId(userId);
	}

	public Order updateStatus(int orderId, String status) {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		order.setOrderStatus(status);
		return orderRepo.save(order);
	}
}
