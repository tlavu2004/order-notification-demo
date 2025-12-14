package com.tlavu.ordernotificationdemo.orderservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderRequestDTO {

    @NotBlank(message = "customerName is required")
    private String customerName;

    @NotNull(message = "totalAmount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "totalAmount must be greater than 0")
    private BigDecimal totalAmount;

    public OrderRequestDTO() {
    }

    public OrderRequestDTO(String customerName, BigDecimal totalAmount) {
        this.customerName = customerName;
        this.totalAmount = totalAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}

