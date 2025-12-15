package com.example.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public Product create(Product product) {
        return repo.save(product);
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product getById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));
    }

    public List<Product> getByCategory(String category) {
        return repo.findByCategory(category);
    }

    public Product update(int id, Product product) {
        Product existing = getById(id);

        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setCategory(product.getCategory());
        existing.setStock(product.getStock());
        existing.setActive(product.getActive());

        return repo.save(existing);
    }

    //for partial update -patch
    public Product partialUpdate(int id, Product product) {

        Product existing = getById(id);

        if (product.getName() != null)
            existing.setName(product.getName());

        if (product.getPrice() != null)
            existing.setPrice(product.getPrice());

        if (product.getCategory() != null)
            existing.setCategory(product.getCategory());

        if (product.getStock() != null)
            existing.setStock(product.getStock());

        if (product.getActive() != null)
            existing.setActive(product.getActive());

        return repo.save(existing);
    }

    public void delete(int id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Product " + id + " not found");
        }
        repo.deleteById(id);
    }
}
