package com.ts.orderservice.service;

import com.ts.common.dto.response.ProductSoldCountDto;
import com.ts.common.dto.response.InventoryResponse;
import com.ts.common.exception.NotFoundException;
import com.ts.orderservice.dto.OrderLineItemDto;
import com.ts.orderservice.dto.OrderRequest;
import com.ts.orderservice.dto.OrderResponse;
import com.ts.orderservice.model.Order;
import com.ts.orderservice.model.OrderItem;
import com.ts.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final String API_INVENTORY = "http://inventory-service/api/v1/inventories";
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;


    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll().stream().map(this::toOrderResponse).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProductSoldCountDto> getProductSoldCount(List<String> skuCodes) {
        return orderRepository.getProductSoldCount(skuCodes);
    }


    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findByUserId(String userId) {
        Order orderExample = Order.builder().userId(userId).build();
        Example<Order> example = Example.of(orderExample);
        List<Order> orders = orderRepository.findAll(example);
        return orders.stream().map(this::toOrderResponse).collect(Collectors.toList());
    }


    @Override
    public OrderResponse findById(Long id) {
        return orderRepository.findById(id).map(this::toOrderResponse).orElseThrow(NotFoundException::new);
    }


    @Override
    public void placeOrder(OrderRequest request) {
        var orderItems = request.items().stream().map(this::toOrderItem).toList();

        var skuCodes = orderItems.stream().map(OrderItem::getSkuCode).toList();

        // Send request to inventory service to check if product is in stock
        var inventoryResponses = webClientBuilder.build().get()
                .uri(String.format("%s/stock", API_INVENTORY), uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        assert inventoryResponses != null;

        var inventories = Arrays.stream(inventoryResponses).collect(Collectors.toMap(InventoryResponse::skuCode, InventoryResponse::stockQuantity));
        orderItems.forEach(orderItem -> {
            var stockQty = inventories.getOrDefault(orderItem.getSkuCode(), 0L);
            if (!inventories.containsKey(orderItem.getSkuCode()))
                throw new IllegalArgumentException("Product not found or not in stock, please try again later");
            if (stockQty - orderItem.getQuantity() < 0)
                throw new IllegalArgumentException("Product is not in stock, please try again later");
        });

        var order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .userId(request.userId())
                .orderLineItemList(orderItems).build();
        orderRepository.save(order);
    }

    private OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUserId())
                .items(order
                        .getOrderLineItemList()
                        .stream()
                        .map(this::toOrderLineItemDto).toList())
                .build();
    }

    private OrderLineItemDto toOrderLineItemDto(OrderItem item) {
        return OrderLineItemDto.builder().skuCode(item.getSkuCode()).price(item.getPrice()).quantity(item.getQuantity()).build();
    }

    private OrderItem toOrderItem(OrderLineItemDto dto) {
        return OrderItem.builder().skuCode(dto.skuCode()).price(dto.price()).quantity(dto.quantity()).build();
    }
}