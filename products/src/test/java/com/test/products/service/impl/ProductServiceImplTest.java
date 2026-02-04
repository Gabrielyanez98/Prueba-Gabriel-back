package com.test.products.service.impl;

import com.test.products.client.ProductClient;
import com.test.products.dto.ProductDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDetail product1;
    private ProductDetail product2;

    @BeforeEach
    void setUp() {
        product1 = new ProductDetail("1", "Product 1", new BigDecimal("19.99"), true);
        product2 = new ProductDetail("2", "Product 2", new BigDecimal("29.99"), false);
    }

    @Test
    void getSimilarProducts_shouldReturnListOfProducts_whenSimilarIdsExist() {
        // Given
        String productId = "1";
        List<String> similarIds = Arrays.asList("2", "3");
        
        when(productClient.getSimilarProductIds(productId)).thenReturn(similarIds);
        when(productClient.getProductDetail("2")).thenReturn(product1);
        when(productClient.getProductDetail("3")).thenReturn(product2);

        // When
        List<ProductDetail> result = productService.getSimilarProducts(productId);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(product1, product2);
        verify(productClient).getSimilarProductIds(productId);
        verify(productClient).getProductDetail("2");
        verify(productClient).getProductDetail("3");
    }

    @Test
    void getSimilarProducts_shouldReturnEmptyList_whenNoSimilarIdsExist() {
        // Given
        String productId = "1";
        when(productClient.getSimilarProductIds(productId)).thenReturn(Collections.emptyList());

        // When
        List<ProductDetail> result = productService.getSimilarProducts(productId);

        // Then
        assertThat(result).isEmpty();
        verify(productClient).getSimilarProductIds(productId);
        verify(productClient, never()).getProductDetail(anyString());
    }

    @Test
    void getSimilarProducts_shouldFilterOutNullProducts_whenClientReturnsNull() {
        // Given
        String productId = "1";
        List<String> similarIds = Arrays.asList("2", "3", "4");
        
        when(productClient.getSimilarProductIds(productId)).thenReturn(similarIds);
        when(productClient.getProductDetail("2")).thenReturn(product1);
        when(productClient.getProductDetail("3")).thenReturn(null); // Simulating a failed fetch
        when(productClient.getProductDetail("4")).thenReturn(product2);

        // When
        List<ProductDetail> result = productService.getSimilarProducts(productId);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(product1, product2);
        verify(productClient).getSimilarProductIds(productId);
        verify(productClient).getProductDetail("2");
        verify(productClient).getProductDetail("3");
        verify(productClient).getProductDetail("4");
    }
}
