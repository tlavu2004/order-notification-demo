package com.tlavu.ordernotificationdemo.orderservice.dto;

import com.tlavu.ordernotificationdemo.orderservice.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDTO {

    private UUID id;
    private String customerName;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private Instant createdAt;
}

