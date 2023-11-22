package com.ts.productservice.controller;

import com.ts.common.exception.NotFoundException;
import com.ts.productservice.dto.ProductRequest;
import com.ts.productservice.dto.ProductResponse;
import com.ts.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all products", description = "Retrieves all products with pagination support")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponse> getAllProducts(@Parameter Pageable pageable) {
        return productService.findAll(pageable);
    }

    @Operation(summary = "Get all product ids", description = "Retrieves the ids of all products")
    @GetMapping("/ids")
    public List<String> getAllProductIds() {
        return productService.findAllIds();
    }

    @Operation(summary = "Get product by id", description = "Retrieves a product by its id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProduct(@PathVariable @Parameter(description = "Product ID") String id) {
        return productService.findById(id).orElseThrow(NotFoundException::new);
    }

    @Operation(summary = "Create a new product", description = "Creates a new product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody @Parameter(description = "Product details") ProductRequest request) {
        productService.createProduct(request);
    }

    @Operation(summary = "Update product", description = "Updates an existing product")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(
            @PathVariable @Parameter(description = "Product ID") String id,
            @RequestBody @Parameter(description = "Updated product details") ProductRequest request) {
        productService.update(id, request);
    }

    /*
    @Operation(summary = "Delete product", description = "Deletes a product by its id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable @Parameter(description = "Product ID") String id) {
        productService.delete(id);
    }
    */
}
