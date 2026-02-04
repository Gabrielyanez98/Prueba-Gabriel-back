package com.test.products.dto;

import java.math.BigDecimal;

public record ProductDetail(
    String id,
    String name,
    BigDecimal price,
    boolean availability
) {}
