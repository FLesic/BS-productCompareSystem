package com.springboot_backend.service;

import com.springboot_backend.dao.Product;

import java.io.IOException;
import java.util.List;

public interface SpiderService {
    // 爬取京东keywords的商品，存储于数据库中，需要进行去重处理
    List<Product> JDSpider(String keywords) throws IOException;

    // 爬取亚马逊keywords的商品，存储于数据库中，需要进行去重处理
    List<Product> AmazonSpider(String keywords) throws IOException;

    // 爬取当当网keywords的商品，存储于数据库中给，需要进行去重处理
    List<Product> DDSpider(String keywords) throws IOException;
}
