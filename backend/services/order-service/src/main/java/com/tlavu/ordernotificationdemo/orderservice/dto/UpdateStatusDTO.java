package com.tlavu.ordernotificationdemo.orderservice.dto;

import com.tlavu.ordernotificationdemo.orderservice.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusDTO {

    @NotNull(message = "status is required")
    private OrderStatus status;
}

