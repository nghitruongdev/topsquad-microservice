package com.ts.common.dto.response;


import lombok.Builder;

@Builder
public record InventoryResponse(String skuCode,
                                Long stockQuantity,
                                boolean isInStock) {}