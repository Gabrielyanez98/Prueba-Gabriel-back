package com.test.products.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleResponseStatusException_shouldReturnFormattedError_when404() {
        // Given
        ResponseStatusException exception = new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Product not found: 9999"
        );

        // When
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleResponseStatusException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("status")).isEqualTo(404);
        assertThat(response.getBody().get("message")).isEqualTo("Product not found: 9999");
        assertThat(response.getBody().get("timestamp")).isNotNull();
    }

    @Test
    void handleResponseStatusException_shouldReturnFormattedError_when500() {
        // Given
        ResponseStatusException exception = new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error"
        );

        // When
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleResponseStatusException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("status")).isEqualTo(500);
        assertThat(response.getBody().get("message")).isEqualTo("Internal server error");
    }
}
