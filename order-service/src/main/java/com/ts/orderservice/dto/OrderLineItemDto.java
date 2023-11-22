package com.ts.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderLineItemDto(
        String skuCode,
         BigDecimal price,
        Integer quantity
) {
}
