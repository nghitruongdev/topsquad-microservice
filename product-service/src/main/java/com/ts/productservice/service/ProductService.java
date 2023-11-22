package com.ts.productservice.service;

import com.ts.productservice.dto.ProductRequest;
import com.ts.productservice.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The ProductService interface defines the operations that can be performed on products.
 */
public interface ProductService {

    /**
     * Creates a new product.
     *
     * @param request the product details
     */
    void createProduct(ProductRequest request);

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    List<ProductResponse> findAll();

    /**
     * Retrieves all products with pagination support.
     *
     * @param page the page details
     * @return a page of products
     */
    Page<ProductResponse> findAll(Pageable page);

    /**
     * Retrieves a product by its id.
     *
     * @param id the id of the product
     * @return an optional containing the product if found, or empty if not found
     */
    Optional<ProductResponse> findById(String id);

    /**
     * Updates a product with new details.
     *
     * @param id      the id of the product to update
     * @param request the updated product details
     */
    void update(String id, ProductRequest request);

    /**
     * Deletes a product by its id.
     *
     * @param id the id of the product to delete
     */
    void delete(String id);

    /**
     * Retrieves the ids of all products.
     *
     * @return a list of all product ids
     */
    List<String> findAllIds();
}