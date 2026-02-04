package com.test.products.controller;

import com.test.products.dto.ProductDetail;
import com.test.products.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void getSimilarProducts_shouldReturnListOfProducts_whenProductsExist() {
        // Given
        String productId = "1";
        List<ProductDetail> products = Arrays.asList(
                new ProductDetail("2", "Dress", new BigDecimal("19.99"), true),
                new ProductDetail("3", "Blazer", new BigDecimal("29.99"), false)
        );
        when(productService.getSimilarProducts(productId)).thenReturn(products);

        // When
        ResponseEntity<List<ProductDetail>> response = productController.getSimilarProducts(productId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).id()).isEqualTo("2");
        assertThat(response.getBody().get(0).name()).isEqualTo("Dress");
        assertThat(response.getBody().get(0).price()).isEqualByComparingTo("19.99");
        assertThat(response.getBody().get(1).id()).isEqualTo("3");
        assertThat(response.getBody().get(1).name()).isEqualTo("Blazer");
    }

    @Test
    void getSimilarProducts_shouldReturnEmptyList_whenNoSimilarProductsExist() {
        // Given
        String productId = "1";
        when(productService.getSimilarProducts(productId)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<ProductDetail>> response = productController.getSimilarProducts(productId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void getSimilarProducts_shouldThrowException_whenProductNotFound() {
        // Given
        String productId = "9999";
        when(productService.getSimilarProducts(productId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + productId));

        // When & Then
        assertThatThrownBy(() -> productController.getSimilarProducts(productId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Product not found: 9999")
                .extracting(e -> ((ResponseStatusException) e).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
