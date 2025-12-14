package com.tlavu.ordernotificationdemo.orderservice.mapper;

import com.tlavu.ordernotificationdemo.orderservice.dto.OrderEventDTO;
import com.tlavu.ordernotificationdemo.orderservice.dto.OrderRequestDTO;
import com.tlavu.ordernotificationdemo.orderservice.dto.OrderResponseDTO;
import com.tlavu.ordernotificationdemo.orderservice.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", expression = "java(com.tlavu.ordernotificationdemo.orderservice.model.OrderStatus.PENDING)")
    Order toEntity(OrderRequestDTO dto);

    OrderResponseDTO toResponseDto(Order order);

    OrderEventDTO toEventDto(Order order);
}

