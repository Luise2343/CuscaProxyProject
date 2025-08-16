package com.proxyproject.products_service.controllers;

import com.proxyproject.products_service.clients.FakeStoreClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final FakeStoreClient fakeStoreClient;

    @GetMapping
    public List<Map<String, Object>> getAllProducts() {
        return fakeStoreClient.getProducts();
    }

    @GetMapping("/{id}")
    public Map<String, Object> getProduct(@PathVariable Long id) {
        return fakeStoreClient.getProductById(id);
    }
}

