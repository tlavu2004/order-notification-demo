package com.tlavu.ordernotificationdemo.notificationservice.kafka;

import com.tlavu.ordernotificationdemo.notificationservice.dto.OrderEventDTO;
import com.tlavu.ordernotificationdemo.notificationservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

    private final NotificationService notificationService;

    public OrderEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "${notification.kafka.topic:order.events.v1}",
            groupId = "${spring.kafka.consumer.group-id:notification-service-group}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(OrderEventDTO event) {
        if (event == null) {
            log.warn("Received null OrderEventDTO");
            return;
        }

        log.info("Consumed OrderEvent: id={}, customer={}, total={}, status={}, createdAt={}",
                event.getId(), event.getCustomerName(), event.getTotalAmount(), event.getStatus(), event.getCreatedAt());

        // Delegate processing to business service
        notificationService.processOrderEvent(event);
    }
}
