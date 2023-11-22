package com.ts.inventoryservice.service;

import com.ts.common.dto.response.InventoryResponse;
import com.ts.common.dto.response.ProductSoldCountDto;
import com.ts.common.dto.response.TotalInventoryDto;
import com.ts.common.exception.NotFoundException;
import com.ts.inventoryservice.dto.InventoryRequest;
import com.ts.inventoryservice.model.Inventory;
import com.ts.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final String API_PRODUCT = "http://product-service/api/v1/products";
    private final String API_ORDER = "http://order-service/api/v1/orders";

    private final InventoryRepository inventoryRepository;
    private final WebClient.Builder clientBuilder;

    @Override
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getStockQuantity(List<String> skuCodes) {
        var soldCounts = clientBuilder.build().get()
                .uri(API_ORDER + "/sold-count", uriBuilder ->
                        uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToFlux(ProductSoldCountDto.class)
                .collect(Collectors.toMap(ProductSoldCountDto::skuCode, ProductSoldCountDto::count))
                .block();
        var totalCounts = inventoryRepository.findSumQuantitiesBySkuCodeIn(skuCodes);

        return toInventoryResponse(soldCounts, totalCounts);
    }

    @Override
    public void initializeData() {
        clientBuilder.build().get()
                .uri(API_PRODUCT + "/ids")
                .retrieve().bodyToMono(String[].class)
                .flatMapMany(Flux::fromArray)
                .map(s -> Inventory.builder()
                        .skuCode(s)
                        .quantity((int) (Math.random() * 20))
                        .build())
                .doOnNext(inventoryRepository::save)
                .subscribe();
    }

    @Override
    public void create(InventoryRequest request) {
        clientBuilder.build().get().uri(
                API_PRODUCT + "/" + request.skuCode())
                .retrieve().bodyToMono(Object.class)
                .doOnError((e) -> {
                    throw new NotFoundException("Product not found");
                })
                .block();
        var inventory = Inventory.builder()
                .skuCode(request.skuCode())
                .quantity(request.quantity())
                .build();
        inventoryRepository.save(inventory);
    }

    @Override
    public void delete(Long id) {
        inventoryRepository.deleteById(id);
    }

    private List<InventoryResponse> toInventoryResponse(Map<String, Long> soldCounts, List<TotalInventoryDto> totalCounts) {
        return totalCounts.stream().map(
                dto -> {
                    var stock = dto.totalQuantity() - soldCounts.getOrDefault(dto.skuCode(), soldCounts.getOrDefault(dto.skuCode(), 0L));
                    return InventoryResponse.builder()
                            .skuCode(dto.skuCode())
                            .stockQuantity(stock)
                            .isInStock(stock > 0)
                            .build();
                }
        ).toList();
    }
}