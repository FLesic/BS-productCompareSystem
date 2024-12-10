package com.springboot_backend.controller;

import com.springboot_backend.Response;
import com.springboot_backend.dao.*;
import com.springboot_backend.dto.PriceHistoryDTO;
import com.springboot_backend.service.CollectService;
import com.springboot_backend.service.HistoryService;
import com.springboot_backend.service.ProductService;
import com.springboot_backend.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SpiderService spiderService;
    @Autowired
    private HistoryService historyService;

    @GetMapping("/product/search/")
    public Response<List<Product>> productSearchByName(@RequestParam("product_name") String product_name){
        try {
            // 从京东中爬取 京东G了
//            List<Product> productListBySearchOfJD = spiderService.JDSpider(product_name);

            // 从亚马逊中爬取
            List<Product> productListBySearchOfAmazon = spiderService.AmazonSpider(product_name);

            // 从当当中爬取
            List<Product> productListBySearchOfDD = spiderService.DDSpider(product_name);

            // 从苏宁中爬取
            List<Product> productListBySearchOfSN = spiderService.SNSpider(product_name);

            // 从数据库中查询
            List<Product> productListInDB = productService.searchProductByFuzzName(product_name);
            // 使用 Set 去重
            Set<Product> productSet = new HashSet<>();
            // 从每个列表中取前 20 个元素
//            productSet.addAll(productListBySearchOfJD.subList(0, Math.min(20, productListBySearchOfJD.size())));
            productSet.addAll(productListBySearchOfSN.subList(0, Math.min(20, productListBySearchOfSN.size())));
            productSet.addAll(productListBySearchOfAmazon.subList(0, Math.min(20, productListBySearchOfAmazon.size())));
            productSet.addAll(productListBySearchOfDD.subList(0, Math.min(20, productListBySearchOfDD.size())));
            productSet.addAll(productListInDB.subList(0, Math.min(20, productListInDB.size())));

            // 转换回 List
            // 将 Set 转换为 List
            List<Product> productList = new ArrayList<>();

            for(Product product:productSet){
                boolean flag = false;
                for(Product product2:productList){
                    if(product.getId().equals(product2.getId())){
                        flag = true;
                        break;
                    }
                }
                if(!flag) productList.add(product);
            }
            // 打乱列表顺序
            Collections.shuffle(productList);

            // 取出前 60 个元素
            List<Product> randomSample = productList.subList(0, Math.min(60, productList.size()));
            return Response.newSuccess(randomSample);

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
            String product_name = product.getName();

            // 从亚马逊中爬取
            List<Product> productListBySearchOfAmazon = spiderService.AmazonSpider(product_name);

            // 从当当中爬取
            List<Product> productListBySearchOfDD = spiderService.DDSpider(product_name);

            // 从苏宁中爬取
            List<Product> productListBySearchOfSN = spiderService.SNSpider(product_name);

            // 从数据库中查询
            List<Product> productListInDB = productService.searchProductByFuzzName(product_name);
            // 使用 Set 去重
            Set<Product> productSet = new HashSet<>();
            productSet.addAll(productListInDB.subList(0, Math.min(5, productListInDB.size())));
            productSet.addAll(productListBySearchOfAmazon.subList(0, Math.min(5, productListBySearchOfAmazon.size())));
            productSet.addAll(productListBySearchOfDD.subList(0, Math.min(5, productListBySearchOfDD.size())));
            productSet.addAll(productListBySearchOfSN.subList(0, Math.min(5, productListBySearchOfSN.size())));// 转换回 List
            // 将 Set 转换为 List
            List<Product> productList = new ArrayList<>();

            for(Product product1: productSet){
                boolean flag = false;
                for(Product product2: productList){
                    if(product1.getId().equals(product2.getId())){
                        flag = true;
                        break;
                    }
                }
                if(!flag) productList.add(product1);
            }
            return Response.newSuccess(productList);
        } catch(Exception e){
            return Response.newFail(e.getMessage());
        }
    }

    @GetMapping("/product/compare-history/")
    public Response<PriceHistoryDTO> productCompareHistory(@RequestParam("product_id") String product_id){
        try{
            List<History> historyList = historyService.getHistoryByProductID(product_id);
            return Response.newSuccess(new PriceHistoryDTO(historyList));
        } catch (Exception e){
            return Response.newFail(e.getMessage());
        }
    }

}
