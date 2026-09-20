package com.example.lab10;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.lab10.model.Product;
import com.example.lab10.repository.ProductRepository;

import reactor.test.StepVerifier;

@SpringBootTest
class Lab10ApplicationTests {

    @Autowired
    private ProductRepository repository;

    @Test
    void contextLoads() {
        // Spring Application Context โหลดสำเร็จ
    }

    @Test
    void testFindById_found() {
        StepVerifier.create(repository.findById("1"))
                .expectNextMatches(p -> p.getName().contains("iPhone"))
                .verifyComplete();
    }

    @Test
    void testFindById_notFound() {
        StepVerifier.create(repository.findById("999"))
                .verifyComplete();
    }

    @Test
    void testFindAll() {
        StepVerifier.create(repository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void testSave() {
        Product newProduct = new Product("4", "iPad Pro",
                "Electronics", "Apple", 15, 35900.0, "NONE");

        StepVerifier.create(repository.save(newProduct))
                .expectNextMatches(p -> p.getId().equals("4") && p.getName().equals("iPad Pro"))
                .verifyComplete();

        StepVerifier.create(repository.findById("4"))
                .expectNextMatches(p -> p.getName().equals("iPad Pro"))
                .verifyComplete();
    }

    @Test
    void testFindByCategory() {
        StepVerifier.create(repository.findByCategory("Electronics"))
                .expectNextCount(3)
                .verifyComplete();
    }
}