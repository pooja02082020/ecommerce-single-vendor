package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.dto.CreateUserRequest;
import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.RoleRepository;
import com.example.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public UserController(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    // ===============================
    // CREATE USER (DEFAULT = CUSTOMER)
    // ===============================
    @PostMapping
    public User createUser(@RequestBody CreateUserRequest request) {

        // fetch CUSTOMER role internally
        Role customerRole = roleRepo.findByName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("CUSTOMER role not found"));

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // encrypt later if needed
        user.setRole(customerRole);              // ✅ DEFAULT ROLE

        return userRepo.save(user);
    }

    // ===============================
    // MAKE ADMIN (ROLE CHANGE)
    // ===============================
    @PutMapping("/make-admin/{id}")
    public User makeAdmin(@PathVariable Integer id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role adminRole = roleRepo.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

        user.setRole(adminRole);
        return userRepo.save(user);
    }

    // ===============================
    // GET USER BY ID 
    // ===============================
    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
