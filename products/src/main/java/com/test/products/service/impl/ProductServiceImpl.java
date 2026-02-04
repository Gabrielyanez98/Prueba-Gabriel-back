package com.test.products.service.impl;

import com.test.products.dto.ProductDetail;
import com.test.products.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public List<ProductDetail> getSimilarProducts(String productId) {
        // TODO: Implement integration logic
        return List.of();
    }
}
