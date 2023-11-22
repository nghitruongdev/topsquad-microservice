package com.ts.orderservice.controller;

import com.ts.common.dto.response.ProductSoldCountDto;
import com.ts.orderservice.dto.OrderRequest;
import com.ts.orderservice.dto.OrderResponse;
import com.ts.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Order API", description = "Operations related to orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all orders", description = "Retrieves a list of all orders")
    public List<OrderResponse> getAllOrders(){
        return orderService.findAllOrders();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get order by ID", description = "Retrieves an order by its ID")
    @ApiResponse(responseCode = "200", description = "Order found", content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    @ApiResponse(responseCode = "404", description = "Order not found")
    public OrderResponse getOrderById(@PathVariable @Parameter(description = "ID of the order") Long id){
        return orderService.findById(id);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get orders by user ID", description = "Retrieves a list of orders for a specific user")
    @ApiResponse(responseCode = "200", description = "Orders found", content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    public List<OrderResponse> getOrdersByUser(@PathVariable @Parameter(description = "ID of the user") String userId){
        return orderService.findByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Place an order", description = "Places a new order")
    @ApiResponse(responseCode = "201", description = "Order placed")
    @ApiResponse(responseCode = "400", description = "Invalid order request")
    public void placeOrder(@RequestBody @Parameter(description = "Order request") OrderRequest request){
        orderService.placeOrder(request);
    }

    @GetMapping("/sold-count")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get product sold count", description = "Retrieves the sold count of products")
    @ApiResponse(responseCode = "200", description = "Product sold count retrieved", content = @Content(schema = @Schema(implementation = ProductSoldCountDto.class)))
    public List<ProductSoldCountDto> getProductSoldCount(@RequestParam("skuCodes") @Parameter(description = "List of SKU codes") List<String> skuCodes){
        return orderService.getProductSoldCount(skuCodes);
    }
}