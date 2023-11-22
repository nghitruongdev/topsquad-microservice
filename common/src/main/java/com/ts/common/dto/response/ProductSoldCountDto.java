package com.ts.common.dto.response;

public record ProductSoldCountDto(
        String skuCode,
        Long count
) {
}
