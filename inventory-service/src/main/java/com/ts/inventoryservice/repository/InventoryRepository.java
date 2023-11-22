package com.ts.inventoryservice.repository;

import com.ts.common.dto.response.TotalInventoryDto;
import com.ts.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkuCodeIn(List<String> skuCodes);

    @Query("SELECT new com.ts.common.dto.response.TotalInventoryDto(i.skuCode, COALESCE(SUM(i.quantity), 0))  FROM Inventory i WHERE i.skuCode IN :skuCodes GROUP BY i.skuCode")
    List<TotalInventoryDto> findSumQuantitiesBySkuCodeIn(@Param("skuCodes") List<String> skuCodes);
}
