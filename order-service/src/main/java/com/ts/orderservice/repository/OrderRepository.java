package com.ts.orderservice.repository;

import com.ts.common.dto.response.ProductSoldCountDto;
import com.ts.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT new com.ts.common.dto.response.ProductSoldCountDto(oi.skuCode, COALESCE(SUM(oi.quantity), 0))\s
            FROM OrderItem oi\s
            WHERE oi.skuCode IN :skuCodes
            GROUP BY oi.skuCode
             """)
    List<ProductSoldCountDto> getProductSoldCount(@Param("skuCodes") List<String> skuCodes);
}
