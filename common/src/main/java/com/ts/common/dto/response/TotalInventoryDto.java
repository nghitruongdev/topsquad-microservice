package com.ts.common.dto.response;

public record TotalInventoryDto(
        String skuCode,
        Long totalQuantity
) {

}
