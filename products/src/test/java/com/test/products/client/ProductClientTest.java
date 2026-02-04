package com.test.products.client;

import com.test.products.dto.ProductDetail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ProductClientTest {

    @Test
    void constructor_shouldCreateClientWithBaseUrl() {
        // Given & When
        ProductClient client = new ProductClient("http://test-url");

        // Then
        assertThat(client).isNotNull();
    }

    @Test
    void getSimilarProductIds_shouldThrowRuntimeException_whenConnectionFails() {
        // Given
        ProductClient client = new ProductClient("http://invalid-url-that-does-not-exist");

        // When & Then
        assertThatThrownBy(() -> client.getSimilarProductIds("9999"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error fetching similar product IDs");
    }

    @Test
    void getProductDetail_shouldThrowRuntimeException_whenConnectionFails() {
        // Given
        ProductClient client = new ProductClient("http://invalid-url-that-does-not-exist");

        // When & Then
        assertThatThrownBy(() -> client.getProductDetail("9999"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error fetching product detail");
    }
}
