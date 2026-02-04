package com.test.products.client;

import com.test.products.dto.ProductDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Component
public class ProductClient {

    private static final Logger logger = LoggerFactory.getLogger(ProductClient.class);
    private final RestClient restClient;

    public ProductClient(@Value("${mock.api.url}") String mockApiUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(mockApiUrl)
                .build();
    }

    public List<String> getSimilarProductIds(String productId) {
        try {
            return restClient.get()
                    .uri("/product/{productId}/similarids", productId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<String>>() {});
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Product with ID {} not found in similarids endpoint", productId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + productId);
        } catch (Exception e) {
            logger.error("Error fetching similar product IDs for productId: {}", productId, e);
            throw new RuntimeException("Error fetching similar product IDs", e);
        }
    }

    public ProductDetail getProductDetail(String productId) {
        try {
            return restClient.get()
                    .uri("/product/{productId}", productId)
                    .retrieve()
                    .body(ProductDetail.class);
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Similar product detail not found for ID: {}", productId);
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + productId);
        } catch (Exception e) {
            logger.error("Error fetching product detail for productId: {}", productId, e);
            throw new RuntimeException("Error fetching product detail", e);
        }
    }
}
