package com.example.lab10.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.lab10.model.Product;
import com.example.lab10.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Mono<Product> getById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found: " + id)));
    }

    public Flux<Product> getAll() {
        return repository.findAll();
    }

    public Mono<Product> save(Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID().toString());
        }
        return repository.save(product);
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

    public Flux<Product> getByCategory(String category) {
        return repository.findByCategory(category);
    }

    public Mono<Double> getDiscountedPrice(String id) {
        return getById(id)
                .map(Product::getDiscountedPrice);
    }
}