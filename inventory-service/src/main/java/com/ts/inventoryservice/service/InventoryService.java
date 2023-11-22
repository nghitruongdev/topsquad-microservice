package com.ts.inventoryservice.service;


import com.ts.common.dto.response.InventoryResponse;
import com.ts.inventoryservice.dto.InventoryRequest;
import com.ts.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryService {

    /**
     * Retrieves a list of all inventory items.
     *
     * @return List of Inventory items
     */
    List<Inventory> findAll();

    /**
     * Retrieves the stock quantity for a list of SKU codes.
     *
     * @param skuCodes List of SKU codes
     * @return List of InventoryResponse objects containing stock quantities
     */
    List<InventoryResponse> getStockQuantity(List<String> skuCodes);

    /**
     * Initializes the inventory with random data.
     */
    void initializeData();

    /**
     * Creates a new inventory item.
     *
     * @param request Inventory item request
     */
    void create(InventoryRequest request);

    /**
     * Deletes an inventory item by ID.
     *
     * @param id ID of the inventory item to be deleted
     */
    void delete(Long id);
}