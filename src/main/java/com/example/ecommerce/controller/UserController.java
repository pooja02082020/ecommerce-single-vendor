package com.example.ecommerce.controller;

import com.example.ecommerce.dto.UserResponse;
import com.example.ecommerce.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse getMyProfile() {
        return userService.getMyProfile();
    }
}
