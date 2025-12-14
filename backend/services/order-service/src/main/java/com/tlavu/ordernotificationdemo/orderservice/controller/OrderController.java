package com.tlavu.ordernotificationdemo.orderservice.controller;

import com.tlavu.ordernotificationdemo.orderservice.dto.OrderRequestDTO;
import com.tlavu.ordernotificationdemo.orderservice.dto.OrderResponseDTO;
import com.tlavu.ordernotificationdemo.orderservice.dto.UpdateStatusDTO;
import com.tlavu.ordernotificationdemo.orderservice.model.OrderStatus;
import com.tlavu.ordernotificationdemo.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO request) {
        OrderResponseDTO created = orderService.createOrder(request);
        URI location = URI.create(String.format("/api/orders/%s", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> list = orderService.getAllOrders();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable("id") UUID id) {
        OrderResponseDTO dto = orderService.getOrder(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable("id") UUID id,
                                                         @Valid @RequestBody UpdateStatusDTO request) {
        OrderStatus newStatus = request.getStatus();
        OrderResponseDTO updated = orderService.updateOrderStatus(id, newStatus);
        return ResponseEntity.ok(updated);
    }
}

