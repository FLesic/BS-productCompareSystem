package com.springboot_backend.service;

import com.springboot_backend.dao.History;
import com.springboot_backend.dao.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.AsyncBoxView;
import java.time.LocalDate;
import java.util.List;
@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    HistoryRepository historyRepository;
    @Override
    public List<History> getHistoryByProductID(String productID) {
        return historyRepository.findByProductID(productID);
    }

    @Override
    public History getHistoryByID(int historyID) {
        return historyRepository.findById(historyID)
                .orElseThrow(() -> new IllegalArgumentException("历史不存在"));
    }

    @Override
    public void addHistory(History history) {
        historyRepository.save(history);
    }

    @Override
    public List<History> getHistoryByProductIDAndDate(String productID, LocalDate date) {
        return historyRepository.findByProductIDAndDate(productID, date);

    }

    @Override
    public History getLastHistoryByProductID(String productID) {
        return historyRepository.findTopByProductIDOrderByDateDesc(productID)
                .orElseThrow(() -> new IllegalArgumentException("历史不存在"));
    }
}
