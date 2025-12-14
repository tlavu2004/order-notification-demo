package com.tlavu.ordernotificationdemo.orderservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NotBlank(message = "customerName is required")
    private String customerName;

    @NotNull(message = "totalAmount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "totalAmount must be greater than 0")
    private BigDecimal totalAmount;
}

