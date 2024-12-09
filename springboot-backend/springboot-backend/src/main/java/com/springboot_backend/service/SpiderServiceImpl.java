package com.springboot_backend.service;

import com.springboot_backend.dao.Product;
import com.springboot_backend.dao.ProductRepository;
import com.springboot_backend.utils.HtmlParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class SpiderServiceImpl implements SpiderService{
    @Autowired
    HtmlParseUtil htmlParseUtil;
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> JDSpider(String keywords) throws IOException {
        List<Product> productList = htmlParseUtil.parseJD(keywords);
        // 去重 + 存储
        // 存储：历史价格表 + 商品表
        for (Product product : productList) {
            productRepository.save(product); // 如果id存在-save更新；否则save存储
            // TODO 存储入历史价格数据库中
        }
        return productList;
    }

    @Override
    public List<Product> AmazonSpider(String keywords) throws IOException {
        List<Product> productList = htmlParseUtil.parseAmazon(keywords);
        // 去重 + 存储
        // 存储：历史价格表 + 商品表
        for (Product product : productList) {
            productRepository.save(product); // 如果id存在-save更新；否则save存储
            // TODO 存储入历史价格数据库中
        }
        return productList;
    }

    @Override
    public List<Product> DDSpider(String keywords) throws IOException {
        List<Product> productList = htmlParseUtil.parseDD(keywords);
        for (Product product : productList) {
            productRepository.save(product);
            // TODO 存储入历史价格数据库中
        }
        return productList;
    }


}
