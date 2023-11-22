package com.ts.productservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductRequest(
        String title,
        BigDecimal price,
        String description,
        String category,
        String image
) {
}
