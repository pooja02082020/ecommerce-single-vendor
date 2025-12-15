package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService service;

	public ProductController(ProductService service) {
		this.service = service;
	}

	// ADMIN
	@PostMapping
	public Product create(@RequestBody Product product) {
		return service.create(product);
	}

	// PUBLIC
	@GetMapping
	public List<Product> getAll() {
		return service.getAll();
	}

	// PUBLIC
	@GetMapping("/{id}")
	public Product getById(@PathVariable int id) {
		return service.getById(id);
	}

	// PUBLIC
	@GetMapping("/category/{category}")
	public List<Product> getByCategory(@PathVariable String category) {
		return service.getByCategory(category);
	}

	// ADMIN
	@PutMapping("/{id}")
	public Product update(@PathVariable int id, @RequestBody Product product) {
		return service.update(id, product);
	}

	// ADMIN
	@PatchMapping("/{id}")
	public Product patchUpdate(@PathVariable int id, @RequestBody Product product) {
		return service.partialUpdate(id, product);
	}

	// ADMIN
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		service.delete(id);
	}
}
