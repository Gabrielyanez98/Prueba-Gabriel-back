package com.test.products.service;

import com.test.products.dto.ProductDetail;
import java.util.List;

public interface ProductService {
    List<ProductDetail> getSimilarProducts(String productId);
}
