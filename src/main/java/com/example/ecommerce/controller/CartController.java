package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    // USER
    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable int userId) {
        return service.getCartByUserId(userId);
    }

    // USER
    @PostMapping("/add")
    public CartItem addItem(@RequestParam int userId,
                            @RequestParam int productId,
                            @RequestParam int quantity) {
        return service.addItem(userId, productId, quantity);
    }

    // USER
    @DeleteMapping("/item/{cartItemId}")
    public void removeItem(@PathVariable int cartItemId) {
        service.removeItem(cartItemId);
    }
}
