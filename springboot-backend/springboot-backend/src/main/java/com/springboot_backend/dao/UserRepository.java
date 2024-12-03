package com.springboot_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByName(String name);
    List<User> findByEmail(String email);
}
