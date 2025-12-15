package com.example.ecommerce.service;

import org.springframework.stereotype.Service;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class CartService {

	private final CartRepository cartRepo;
	private final CartItemRepository cartItemRepo;
	private final ProductRepository productRepo;

	public CartService(CartRepository cartRepo, CartItemRepository cartItemRepo, ProductRepository productRepo) {
		this.cartRepo = cartRepo;
		this.cartItemRepo = cartItemRepo;
		this.productRepo = productRepo;
	}

	public Cart getCartByUserId(int userId) {
		return cartRepo.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
	}

	public CartItem addItem(int userId, int productId, int quantity) {

		Cart cart = getCartByUserId(userId);

		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		CartItem item = new CartItem();
		item.setCart(cart);
		item.setProduct(product);
		item.setQuantity(quantity);
		item.setUnitPrice(product.getPrice());

		cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));

		return cartItemRepo.save(item);
	}

	public void removeItem(int cartItemId) {
		if (!cartItemRepo.existsById(cartItemId)) {
			throw new ResourceNotFoundException("Cart item not found");
		}
		cartItemRepo.deleteById(cartItemId);
	}
}
