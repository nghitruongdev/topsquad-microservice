package com.ts.productservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        String id,
        String title,
        String description,
        BigDecimal price,
        String category,
        String image
) {
}


