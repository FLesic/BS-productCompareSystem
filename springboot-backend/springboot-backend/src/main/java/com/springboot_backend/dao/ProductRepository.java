package com.springboot_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    // 使用 Spring Data JPA 的命名约定来定义模糊搜索方法
    List<Product> findByNameContainingIgnoreCase(String name);

    // 使用 Spring Data JPA 的命名约定来定义模糊搜索方法
    List<Product> findByDetailContainingIgnoreCase(String name);
}
