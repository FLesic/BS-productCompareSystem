package com.springboot_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectRepository extends JpaRepository<Collect, Integer> {
    List<Collect> findByUserID(Integer user_id);
    List<Collect> findByProductID(String product_id);
    List<Collect> findByUserIDAndProductID(Integer user_id, String product_id);
    void deleteByUserIDAndProductID(Integer user_id, String product_id);
}
