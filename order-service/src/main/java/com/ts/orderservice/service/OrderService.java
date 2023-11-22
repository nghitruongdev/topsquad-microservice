package com.ts.orderservice.service;

import com.ts.common.dto.response.ProductSoldCountDto;
import com.ts.common.exception.NotFoundException;
import com.ts.orderservice.dto.OrderRequest;
import com.ts.orderservice.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    /**
     * Places an order based on the provided order request.
     * Depends on inventory service for stock quantity check.
     * @param request OrderRequest containing the details of the order to be placed.
     */
    void placeOrder(OrderRequest request);

    /**
     * Retrieves all orders.
     *
     * @return List of OrderResponse containing details of each order.
     */
    List<OrderResponse> findAllOrders();

    /**
     * Retrieves an order by its ID.
     *
     * @param id ID of the order.
     * @return OrderResponse containing the details of the order.
     * @throws NotFoundException if the order is not found.
     */
    OrderResponse findById(Long id);

    /**
     * Retrieves all orders for a specific user.
     *
     * @param userId ID of the user.
     * @return List of OrderResponse containing details of each order for the given user.
     */
    List<OrderResponse> findByUserId(String userId);


    /**
     * Retrieves the sold count of products based on their SKU codes.
     *
     * @param skuCodes List of SKU codes for which the sold count is to be retrieved.
     * @return List of ProductSoldCountDto containing the sold count of each product.
     */
    List<ProductSoldCountDto> getProductSoldCount(List<String> skuCodes);



}
