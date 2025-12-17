package com.example.ecommerce.controller;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.CartService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService service;
    private final UserRepository userRepo;

    public CartController(CartService service, UserRepository userRepo) {
        this.service = service;
        this.userRepo = userRepo;
    }

    private User getUser(Authentication auth) {
        return userRepo.findByEmail(auth.getName()).orElseThrow();
    }

    @GetMapping
    public Cart getCart(Authentication auth) {
        return service.getCart(getUser(auth));
    }

    @PostMapping("/add")
    public Cart addToCart(
            Authentication auth,
            @RequestParam long productId,
            @RequestParam int quantity) {

        return service.addToCart(getUser(auth), productId, quantity);
    }
    
    @PutMapping("/update/{itemId}")
    public Cart updateQuantity(
            Authentication auth,
            @PathVariable long itemId,
            @RequestParam int quantity) {

        return service.updateQuantity(getUser(auth), itemId, quantity);
    }


    @DeleteMapping("/remove/{itemId}")
    public Cart removeItem(Authentication auth, @PathVariable long itemId) {
        return service.removeItem(getUser(auth), itemId);
    }

    @DeleteMapping("/clear")
    public void clearCart(Authentication auth) {
        service.clearCart(getUser(auth));
    }
}
