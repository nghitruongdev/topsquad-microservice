package com.ts.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.CascadeType;
import java.util.List;

@Entity
@Table(name = "t_order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderNumber;
    private String userId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderLineItemList;
}
