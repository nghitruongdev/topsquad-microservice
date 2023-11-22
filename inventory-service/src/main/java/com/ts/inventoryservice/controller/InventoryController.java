package com.ts.inventoryservice.controller;

import com.ts.common.dto.response.InventoryResponse;
import com.ts.inventoryservice.dto.InventoryRequest;
import com.ts.inventoryservice.model.Inventory;
import com.ts.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all inventory items", description = "Retrieves a list of all inventory items")
    public List<Inventory> getAll() {
        return inventoryService.findAll();
    }

    @GetMapping("/stock")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get stock quantity", description = "Retrieves the stock quantity for a list of SKU codes")
    @ApiResponse(responseCode = "200", description = "Stock quantity retrieved",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventoryResponse.class)))
    public List<InventoryResponse> getStockQuantity(
            @RequestParam("skuCodes") @Parameter(description = "List of SKU codes") List<String> skuCodes
    ) {
        return inventoryService.getStockQuantity(skuCodes);
    }

    @GetMapping("/init-data")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Initialize inventory data", description = "Initializes the inventory with sample data")
    public String initializeData() {
        inventoryService.initializeData();
        return "Initialized data";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create an inventory item", description = "Creates a new inventory item")
    public void createInventory(
            @RequestBody @Parameter(description = "Inventory item request") InventoryRequest request
    ) {
        inventoryService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an inventory item", description = "Deletes an inventory item by ID")
    @ApiResponse(responseCode = "204", description = "Inventory item deleted")
    public void deleteInventory(@PathVariable @Parameter(description = "ID of the inventory item") Long id) {
        inventoryService.delete(id);
    }
}
