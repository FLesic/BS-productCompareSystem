package com.springboot_backend.dto;

import com.springboot_backend.dao.History;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PriceHistoryDTO {
    private List<LocalDate> date;
    private List<Double> price;

    // Constructor
    public PriceHistoryDTO(List<LocalDate> date, List<Double> price) {
        this.date = date;
        this.price = price;
    }

    public PriceHistoryDTO(List<History> historyList) {
        List<LocalDate> _date = new ArrayList<>();
        List<Double> _price = new ArrayList<>();
        for (History history : historyList) {
            _date.add(history.getDate());
            _price.add(history.getPrice());
        }
        this.date = _date;
        this.price = _price;
    }

    // Getters and setters
    public List<LocalDate> getDate() {
        return date;
    }

    public void setDate(List<LocalDate> date) {
        this.date = date;
    }

    public List<Double> getPrice() {
        return price;
    }

    public void setPrice(List<Double> price) {
        this.price = price;
    }
}