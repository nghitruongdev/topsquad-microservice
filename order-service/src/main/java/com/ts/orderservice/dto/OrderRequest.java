package com.ts.orderservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderRequest(
        List<OrderLineItemDto> items,

        String userId) {
}
