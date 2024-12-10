package com.springboot_backend.dao;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "history")
public class History {
    @Id
    @Column(name = "history_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_id")
    private String productID;

    @Column(name = "history_date")
    private LocalDate date;

    @Column(name = "price")
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
