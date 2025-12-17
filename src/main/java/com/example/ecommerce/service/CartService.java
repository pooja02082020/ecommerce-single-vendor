package com.example.ecommerce.service;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;

import org.springframework.stereotype.Service;

@Service
public class CartService {

	private final CartRepository cartRepo;
	private final CartItemRepository itemRepo;
	private final ProductRepository productRepo;

	public CartService(CartRepository cartRepo, CartItemRepository itemRepo, ProductRepository productRepo) {
		this.cartRepo = cartRepo;
		this.itemRepo = itemRepo;
		this.productRepo = productRepo;
	}

	public Cart getCart(User user) {
		return cartRepo.findByUser(user).orElseGet(() -> {
			Cart cart = new Cart();
			cart.setUser(user);
			return cartRepo.save(cart);
		});
	}

	public Cart addToCart(User user, long productId, int quantity) {

		Cart cart = getCart(user);

		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		for (CartItem item : cart.getItems()) {
			if (item.getProduct().getId().equals(productId)) {
				item.setQuantity(item.getQuantity() + quantity);
				return cartRepo.save(cart);
			}
		}

		CartItem item = new CartItem();
		item.setCart(cart);
		item.setProduct(product);
		item.setQuantity(quantity);

		cart.getItems().add(item);

		return cartRepo.save(cart);
	}
	
	public Cart updateQuantity(User user, long itemId, int quantity) {

	    if (quantity < 0) {
	        throw new IllegalArgumentException("Quantity cannot be negative");
	    }

	    Cart cart = getCart(user);

	    for (CartItem item : cart.getItems()) {

	        if (item.getId().equals(itemId)) {

	            if (quantity == 0) {
	                cart.getItems().remove(item);
	            } else {
	                item.setQuantity(quantity);
	            }

	            return cartRepo.save(cart);
	        }
	    }

	    throw new ResourceNotFoundException("Cart item not found");
	}


	public Cart removeItem(User user, long itemId) {
		Cart cart = getCart(user);
		cart.getItems().removeIf(i -> i.getId().equals(itemId));
		return cartRepo.save(cart);
	}

	public void clearCart(User user) {
		Cart cart = getCart(user);
		cart.getItems().clear();
		cartRepo.save(cart);
	}
}
