package com.springboot_backend.service;

import com.springboot_backend.dao.Product;
import com.springboot_backend.dao.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("商品不存在"));
    }

    @Override
    public List<Product> searchProductByFuzzName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Product> searchProductByFuzzDetail(String detail) {
        return productRepository.findByNameContainingIgnoreCase(detail);
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }
}
