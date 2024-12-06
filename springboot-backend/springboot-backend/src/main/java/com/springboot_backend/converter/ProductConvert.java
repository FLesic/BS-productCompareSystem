package com.springboot_backend.converter;

import com.springboot_backend.dao.Product;
import com.springboot_backend.dto.ProductDTO;

public class ProductConvert {
    public static ProductDTO convertProduct(Product product, int lowReminderFlag, int collectFlag, int count) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setPlatform(product.getPlatform());
        productDTO.setPhotoURL(product.getPhotoURL());
        productDTO.setProductURL(product.getProductURL());
        productDTO.setShop(product.getShop());
        productDTO.setLowReminderFlag(lowReminderFlag);
        productDTO.setCollectFlag(collectFlag);
        productDTO.setCount(count);
        return productDTO;
    }
}
