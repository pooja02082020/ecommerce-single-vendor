package com.example.ecommerce.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
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

    @CacheEvict(
        value = { "products", "product", "productsByCategory" },
        allEntries = true
    )
    public Product create(Product product) {
        return repo.save(product);
    }

    @Cacheable(value = "products", key = "'all'")
    public List<Product> getAll() {
        return repo.findAll();
    }

    @Cacheable(
        value = "products",
        key = "'page=' + #page + ',size=' + #size + ',sort=' + #sortBy + ',dir=' + #direction"
    )
    public Page<Product> getAllPaginated(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return repo.findAll(pageable);
    }

    @Cacheable(value = "product", key = "#id")
    public Product getById(long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product " + id + " not found"));
    }

    @Cacheable(value = "productsByCategory", key = "#category")
    public List<Product> getByCategory(String category) {
        return repo.findByCategory(category);
    }

    @CacheEvict(
        value = { "products", "product", "productsByCategory" },
        allEntries = true
    )
    public Product update(long id, Product product) {

        Product existing = getById(id);

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setCategory(product.getCategory());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());
        existing.setActive(product.getActive());

        return repo.save(existing);
    }

    @CacheEvict(
        value = { "products", "product", "productsByCategory" },
        allEntries = true
    )
    public Product partialUpdate(long id, Product product) {

        Product existing = getById(id);

        if (product.getName() != null)
            existing.setName(product.getName());

        if (product.getDescription() != null)
            existing.setDescription(product.getDescription());

        if (product.getCategory() != null)
            existing.setCategory(product.getCategory());

        if (product.getPrice() != null)
            existing.setPrice(product.getPrice());

        if (product.getStock() != null)
            existing.setStock(product.getStock());

        if (product.getActive() != null)
            existing.setActive(product.getActive());

        return repo.save(existing);
    }

    @CacheEvict(
        value = { "products", "product", "productsByCategory" },
        allEntries = true
    )
    public void delete(long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Product " + id + " not found");
        }
        repo.deleteById(id);
    }
}
