package com.springboot_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    List<History> findByProductID(String productID);
    List<History> findByProductIDAndDate(String productID, LocalDate date);
    Optional<History> findTopByProductIDOrderByDateDesc(String productID);
}
