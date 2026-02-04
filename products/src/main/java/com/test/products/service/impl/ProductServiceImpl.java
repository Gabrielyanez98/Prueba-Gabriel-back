package com.test.products.service.impl;

import com.test.products.client.ProductClient;
import com.test.products.dto.ProductDetail;
import com.test.products.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductClient productClient;

    public ProductServiceImpl(ProductClient productClient) {
        this.productClient = productClient;
    }

    @Override
    public List<ProductDetail> getSimilarProducts(String productId) {
        List<String> similarIds = productClient.getSimilarProductIds(productId);
        
        return similarIds.stream()
                .map(productClient::getProductDetail)
                .filter(java.util.Objects::nonNull)
                .toList();
    }
}
