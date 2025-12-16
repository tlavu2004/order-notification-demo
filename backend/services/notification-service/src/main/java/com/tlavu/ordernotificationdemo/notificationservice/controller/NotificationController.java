package com.tlavu.ordernotificationdemo.notificationservice.controller;

import com.tlavu.ordernotificationdemo.notificationservice.model.NotificationLog;
import com.tlavu.ordernotificationdemo.notificationservice.repository.NotificationLogRepository;
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

    private final NotificationLogRepository repository;

    public NotificationController(NotificationLogRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<NotificationLog>> getAll() {
        List<NotificationLog> all = repository.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<NotificationLog>> getByOrderId(@PathVariable("orderId") String orderId) {
        log.info("Fetching notification logs for orderId={}", orderId);
        List<NotificationLog> list = repository.findByOrderId(orderId);
        return ResponseEntity.ok(list);
    }
}

