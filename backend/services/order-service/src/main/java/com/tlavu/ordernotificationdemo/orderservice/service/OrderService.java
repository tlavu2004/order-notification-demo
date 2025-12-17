package com.tlavu.ordernotificationdemo.orderservice.service;

import com.tlavu.ordernotificationdemo.orderservice.dto.OrderRequestDTO;
import com.tlavu.ordernotificationdemo.orderservice.dto.OrderResponseDTO;
import com.tlavu.ordernotificationdemo.orderservice.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO request);

    OrderResponseDTO getOrder(UUID orderId);

    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO updateOrderStatus(UUID orderId, OrderStatus newStatus);
}

