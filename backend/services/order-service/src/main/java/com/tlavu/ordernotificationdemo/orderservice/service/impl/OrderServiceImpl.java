package com.tlavu.ordernotificationdemo.orderservice.service.impl;

import com.tlavu.ordernotificationdemo.orderservice.dto.OrderEventDTO;
import com.tlavu.ordernotificationdemo.orderservice.dto.OrderRequestDTO;
import com.tlavu.ordernotificationdemo.orderservice.dto.OrderResponseDTO;
import com.tlavu.ordernotificationdemo.orderservice.exception.OrderNotFoundException;
import com.tlavu.ordernotificationdemo.orderservice.kafka.OrderEventProducer;
import com.tlavu.ordernotificationdemo.orderservice.mapper.OrderMapper;
import com.tlavu.ordernotificationdemo.orderservice.model.Order;
import com.tlavu.ordernotificationdemo.orderservice.model.OrderStatus;
import com.tlavu.ordernotificationdemo.orderservice.repository.OrderRepository;
import com.tlavu.ordernotificationdemo.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderEventProducer orderEventProducer;
    private final String topic;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderMapper orderMapper,
                            OrderEventProducer orderEventProducer,
                            @Value("${order.events.topic:orders.events}") String topic) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderEventProducer = orderEventProducer;
        this.topic = topic;
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        Order entity = orderMapper.toEntity(request);
        Order saved = orderRepository.save(entity);

        OrderResponseDTO response = orderMapper.toResponseDto(saved);

        OrderEventDTO event = orderMapper.toEventDto(saved);
        orderEventProducer.sendOrderEvent(event);

        return response;
    }

    @Override
    public OrderResponseDTO getOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
        return orderMapper.toResponseDto(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(UUID orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));

        order.setStatus(newStatus);
        Order saved = orderRepository.save(order);

        OrderEventDTO event = orderMapper.toEventDto(saved);
        orderEventProducer.sendOrderEvent(event);

        return orderMapper.toResponseDto(saved);
    }
}
