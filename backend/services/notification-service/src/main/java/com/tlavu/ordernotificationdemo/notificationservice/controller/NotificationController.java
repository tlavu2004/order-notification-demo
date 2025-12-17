package com.tlavu.ordernotificationdemo.notificationservice.controller;

import com.tlavu.ordernotificationdemo.notificationservice.model.NotificationLog;
import com.tlavu.ordernotificationdemo.notificationservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationLog>> getAll() {
        List<NotificationLog> logs = notificationService.findAll();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<NotificationLog>> getByOrderId(
            @PathVariable String orderId
    ) {
        log.info("Fetching notification logs for orderId={}", orderId);
        List<NotificationLog> logs = notificationService.findByOrderId(orderId);
        return ResponseEntity.ok(logs);
    }
}

