package com.springboot_backend.controller;

import com.springboot_backend.Response;
import com.springboot_backend.dao.Collect;
import com.springboot_backend.dao.Product;
import com.springboot_backend.dao.User;
import com.springboot_backend.service.CollectService;
import com.springboot_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CollectService collectService;

    @GetMapping("/product/search/")
    public Response<List<Product>> productSearchByName(@RequestParam("product_name") String product_name){
        // 对product_name进行分词处理
        // 每一个小分词通过模糊搜索返回相关的商品信息
        // 最终组合所有的商品信息

        try {
            //TODO 分词处理 爬虫处理
            List<Product> productListByName = productService.searchProductByFuzzName(product_name);
            return Response.newSuccess(productListByName);
        } catch (Exception e) {
            // 处理异常
            return Response.newFail(e.getMessage());
        }
    }

    @GetMapping("/product/get/")
    public Response<Product> productGetById(@RequestParam("product_id") String product_id){
        try {
            return Response.newSuccess(productService.getProductById(product_id));
        } catch (Exception e) {
            // 处理异常
            return Response.newFail(e.getMessage());
        }
    }

    @GetMapping("/product/compare-platform/")
    public Response<List<Product>> productComparePlatform(@RequestParam("product_id") String product_id){
        try{
            Product product = productService.getProductById(product_id);
            List<Product> productListByName = productService.searchProductByFuzzName(product.getName());
            return Response.newSuccess(productListByName);
        } catch(Exception e){
            return Response.newFail(e.getMessage());
        }
    }


}
