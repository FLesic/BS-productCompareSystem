package com.springboot_backend.controller;

import com.springboot_backend.Response;
import com.springboot_backend.converter.ProductConvert;
import com.springboot_backend.dao.Collect;
import com.springboot_backend.dao.Product;
import com.springboot_backend.dto.ProductDTO;
import com.springboot_backend.service.CollectService;
import com.springboot_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CollectController {
    @Autowired
    private CollectService collectService;
    @Autowired
    private ProductService productService;

    @PostMapping("/collect/set/")
    public Response<Integer> productCollect(@RequestBody Map<String, String> collectData){
        try {
            int user_id = Integer.parseInt(collectData.get("user_id"));
            String product_id = collectData.get("product_id");
            return Response.newSuccess(collectService.addNewCollect(user_id, product_id));
        } catch (Exception e){
            return Response.newFail(e.getMessage());
        }
    }

    @PostMapping("/collect/cancel/")
    public Response<Integer> productCancelCollect(@RequestBody Map<String, String> collectData){
        try {
            int user_id = Integer.parseInt(collectData.get("user_id"));
            String product_id = collectData.get("product_id");
            return Response.newSuccess(collectService.deleteCollectByUserAndProduct(user_id, product_id));
        } catch (Exception e){
            return Response.newFail(e.getMessage());
        }
    }

    @PostMapping("/collect/update/")
    public Response<Integer> productUpdateCollect(@RequestBody Map<String, String> collectData){
        try {
            int user_id = Integer.parseInt(collectData.get("user_id"));
            String product_id = collectData.get("product_id");
            int isLowReminder = Integer.parseInt(collectData.get("isLowReminder"));
            return Response.newSuccess(collectService.updateCollect(user_id, product_id, isLowReminder));
        } catch (Exception e){
            return Response.newFail(e.getMessage());
        }
    }

    @GetMapping("/collect/get/")
    public Response<List<Collect>> productGetCollect(@RequestParam("user_id") int user_id, @RequestParam("product_id") String product_id){
        try {
            return Response.newSuccess(collectService.getCollectByUserAndProduct(user_id, product_id));
        } catch (Exception e){
            return Response.newFail(e.getMessage());
        }
    }

    @GetMapping("/collect/getAll/")
    public Response<List<ProductDTO>> productGetAllCollect(@RequestParam("user_id") int user_id){
        try{
            List<Collect> collectList = collectService.getCollectByUser(user_id); // 取出用户所有收藏
            List<ProductDTO> productDTOList = new ArrayList<>();
            int count = 0;
            for(Collect collect : collectList){
                String product_id = collect.getProductID();
                Product product = productService.getProductById(product_id);
                ProductDTO productDTO = ProductConvert.convertProduct(product, collect.getIsLowReminder(), 1, count);
                productDTOList.add(productDTO);
                count = count + 1;
            }
            return Response.newSuccess(productDTOList);
        }catch(Exception e){
            return Response.newFail(e.getMessage());
        }
    }
}
