package com.tlavu.ordernotificationdemo.notificationservice.service.impl;

import com.tlavu.ordernotificationdemo.notificationservice.dto.OrderEventDTO;
import com.tlavu.ordernotificationdemo.notificationservice.model.NotificationLog;
import com.tlavu.ordernotificationdemo.notificationservice.model.NotificationStatus;
import com.tlavu.ordernotificationdemo.notificationservice.model.NotificationType;
import com.tlavu.ordernotificationdemo.notificationservice.repository.NotificationLogRepository;
import com.tlavu.ordernotificationdemo.notificationservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationLogRepository repository;

    public NotificationServiceImpl(NotificationLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void processOrderEvent(OrderEventDTO event) {
        if (event == null) {
            log.warn("processOrderEvent called with null event");
            return;
        }

        // Map event status/type to a notification message
        String message = mapEventToMessage(event);
        NotificationType type = mapEventToType(event);

        NotificationLog entry = new NotificationLog();
        entry.setOrderId(event.getId() != null ? event.getId().toString() : null);
        entry.setMessage(message);
        entry.setType(type);
        entry.setStatus(NotificationStatus.PENDING);
        entry.setSentAt(Instant.now());

        NotificationLog saved = saveNotificationLog(entry);

        boolean sent = sendNotification(saved);
        saved.setStatus(sent ? NotificationStatus.SENT : NotificationStatus.FAILED);
        repository.save(saved);
    }

    @Override
    public boolean sendNotification(NotificationLog logEntry) {
        // Mock send: just log to console - replace with mail/SMS provider integration
        log.info("Sending notification for orderId={} message={}", logEntry.getOrderId(), logEntry.getMessage());
        return true;
    }

    @Override
    public NotificationLog saveNotificationLog(NotificationLog logEntry) {
        return repository.save(logEntry);
    }

    private String mapEventToMessage(OrderEventDTO event) {
        String status = event.getStatus();
        return switch (status == null ? "" : status) {
            case "PENDING" -> "Your order " + event.getId() + " is pending confirmation.";
            case "CONFIRMED" -> "Your order " + event.getId() + " has been confirmed.";
            case "SHIPPED" -> "Your order " + event.getId() + " has been shipped.";
            case "DELIVERED" -> "Your order " + event.getId() + " was delivered.";
            case "CANCELLED" -> "Your order " + event.getId() + " was cancelled.";
            default -> "Order " + event.getId() + " status updated: " + status;
        };
    }

    private NotificationType mapEventToType(OrderEventDTO event) {
        String status = event.getStatus();
        return switch (status == null ? "" : status) {
            case "PENDING" -> NotificationType.ORDER_CREATED;
            case "CANCELLED" -> NotificationType.ORDER_CANCELLED;
            default -> NotificationType.ORDER_UPDATED;
        };
    }
}
