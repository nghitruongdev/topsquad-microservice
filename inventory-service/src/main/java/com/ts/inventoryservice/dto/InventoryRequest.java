package com.ts.inventoryservice.dto;

public record InventoryRequest(
        String skuCode,
        Integer quantity
) {
}
