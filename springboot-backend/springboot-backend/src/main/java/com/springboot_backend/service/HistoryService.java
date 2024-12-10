package com.springboot_backend.service;

import com.springboot_backend.dao.History;

import java.time.LocalDate;
import java.util.List;

public interface HistoryService {
    List<History> getHistoryByProductID(String productID);
    History getHistoryByID(int historyID);
    void addHistory(History history);
    List<History> getHistoryByProductIDAndDate(String productID, LocalDate date);
    History getLastHistoryByProductID(String productID);
}
