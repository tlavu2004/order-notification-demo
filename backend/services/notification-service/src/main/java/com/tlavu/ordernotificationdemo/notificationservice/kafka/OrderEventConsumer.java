package com.tlavu.ordernotificationdemo.notificationservice.kafka;

import com.tlavu.ordernotificationdemo.notificationservice.dto.OrderEventDTO;
import com.tlavu.ordernotificationdemo.notificationservice.model.NotificationLog;
import com.tlavu.ordernotificationdemo.notificationservice.model.NotificationStatus;
import com.tlavu.ordernotificationdemo.notificationservice.model.NotificationType;
import com.tlavu.ordernotificationdemo.notificationservice.repository.NotificationLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OrderEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

    private final NotificationLogRepository repository;

    public OrderEventConsumer(NotificationLogRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "${notification.kafka.topic:order-events}",
            groupId = "${spring.kafka.consumer.group-id:notification-service-group}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(OrderEventDTO event) {
        if (event == null) {
            log.warn("Received null OrderEventDTO");
            return;
        }

        log.info("Consumed OrderEvent: id={}, customer={}, total={}, status={}, createdAt={}",
                event.getId(), event.getCustomerName(), event.getTotalAmount(), event.getStatus(), event.getCreatedAt());

        // Persist a notification log
        NotificationLog logEntry = new NotificationLog();
        logEntry.setOrderId(event.getId() != null ? event.getId().toString() : null);
        logEntry.setMessage("Order event: " + event.getStatus() + " for order " + event.getId());
        logEntry.setType(NotificationType.ORDER_UPDATED);
        logEntry.setStatus(NotificationStatus.PENDING);
        logEntry.setSentAt(Instant.now());

        repository.save(logEntry);
    }
}

