package com.example.ecommerce.service;

import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class OrderService {

	private final OrderRepository orderRepo;
	private final CartRepository cartRepo;

	public OrderService(OrderRepository orderRepo, CartRepository cartRepo) {
		this.orderRepo = orderRepo;
		this.cartRepo = cartRepo;
	}

	public Order placeOrder(User user) {

		Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found"));

		if (cart.getItems().isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}

		Order order = new Order();
		order.setUser(user);

		double total = 0;

		for (CartItem cartItem : cart.getItems()) {

			OrderItem item = new OrderItem();
			item.setOrder(order);
			item.setProduct(cartItem.getProduct());
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(cartItem.getProduct().getPrice());

			total += cartItem.getQuantity() * cartItem.getProduct().getPrice();
			order.getItems().add(item);
		}

		order.setTotalAmount(total);

		cart.getItems().clear();
		cartRepo.save(cart);

		return orderRepo.save(order);
	}

	public Order simulatePayment(Long orderId) {

		Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		if (order.getPaymentStatus() == PaymentStatus.SUCCESS) {
			throw new RuntimeException("Order already paid");
		}

		boolean paymentSuccess = new Random().nextBoolean();

		if (paymentSuccess) {
			order.setPaymentStatus(PaymentStatus.SUCCESS);
			order.setOrderStatus(OrderStatus.PAID);
		} else {
			order.setPaymentStatus(PaymentStatus.FAILED);
		}

		return orderRepo.save(order);
	}

	public List<Order> myOrders(User user) {
		return orderRepo.findByUser(user);
	}

	public Order getOrder(Long id) {
		return orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
	}
}
