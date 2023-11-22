package com.ts.orderservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderResponse(
        Long id,
        String orderNumber,
        String userId,
        List<OrderLineItemDto> items
) {
}
