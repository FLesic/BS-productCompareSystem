package com.springboot_backend.service;

import com.springboot_backend.dao.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(String id);
    List<Product> searchProductByFuzzName(String name);
    List<Product> searchProductByFuzzDetail(String description);
    void addProduct(Product product);
}
