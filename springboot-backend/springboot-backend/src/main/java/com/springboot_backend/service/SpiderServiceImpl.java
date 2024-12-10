package com.springboot_backend.service;

import com.springboot_backend.dao.History;
import com.springboot_backend.dao.HistoryRepository;
import com.springboot_backend.dao.Product;
import com.springboot_backend.dao.ProductRepository;
import com.springboot_backend.utils.HtmlParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SpiderServiceImpl implements SpiderService{
    @Autowired
    HtmlParseUtil htmlParseUtil;
    @Autowired
    ProductService productService;
    @Autowired
    HistoryService historyService;

    @Override
    public List<Product> JDSpider(String keywords) throws IOException {
        List<Product> productList = htmlParseUtil.parseJD(keywords);
        // 去重 + 存储
        // 存储：历史价格表 + 商品表
        StoreProductAndDate(productList);
        return productList;
    }

    @Override
    public List<Product> AmazonSpider(String keywords) throws IOException {
        List<Product> productList = htmlParseUtil.parseAmazon(keywords);
        // 去重 + 存储
        // 存储：历史价格表 + 商品表
        StoreProductAndDate(productList);
        return productList;
    }

    @Override
    public List<Product> DDSpider(String keywords) throws IOException {
        List<Product> productList = htmlParseUtil.parseDD(keywords);
        StoreProductAndDate(productList);
        return productList;
    }

    @Override
    public List<Product> SNSpider(String keywords) throws IOException {
        List<Product> productList = htmlParseUtil.parseSN(keywords);
        StoreProductAndDate(productList);
        return productList;
    }

    @Override
    public void StoreProductAndDate(List<Product> productList) throws IOException {
        LocalDate date = LocalDate.now();
        for(Product product : productList) {
            productService.addProduct(product);
            List<History> historyList = historyService.getHistoryByProductIDAndDate(product.getId(), date);
            // 一天最多一次价格
            if(historyList.isEmpty()) {
                History history = new History();
                history.setDate(date);
                history.setPrice(product.getPrice());
                history.setProductID(product.getId());
                historyService.addHistory(history);
            }
        }
    }


}
