package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Role;
import com.example.ecommerce.repository.RoleRepository;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleRepository roleRepo;

    public RoleController(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleRepo.save(role);
    }
}
