package com.springboot_backend.dao;

import jakarta.persistence.*;

@Entity
@Table(name = "collect")
public class Collect {
    @Id
    @Column(name = "collect_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int collectID;

    @Column(name = "user_id")
    private int userID;

    @Column(name = "product_id")
    private String productID;

    @Column(name = "islowreminder")
    private int isLowReminder;

    public int getCollectID() {
        return collectID;
    }

    public void setCollectID(int collectID) {
        this.collectID = collectID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getIsLowReminder() {
        return isLowReminder;
    }

    public void setIsLowReminder(int isLowReminder) {
        this.isLowReminder = isLowReminder;
    }
}
