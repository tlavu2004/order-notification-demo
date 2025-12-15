package com.tlavu.ordernotificationdemo.orderservice.kafka;

import com.tlavu.ordernotificationdemo.orderservice.dto.OrderEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventProducer.class);

    private final KafkaTemplate<String, OrderEventDTO> kafkaTemplate;
    private final String topic;

    public OrderEventProducer(
            KafkaTemplate<String, OrderEventDTO> kafkaTemplate,
            @Value("${order.events.topic:order-events}") String topic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendOrderEvent(OrderEventDTO event) {
        String key = event.getId().toString();
        LOGGER.debug("Sending order event to topic {} with key {}", topic, key);

        kafkaTemplate.send(topic, key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        LOGGER.error("Failed to send order event for key={}: {}", key, ex.getMessage(), ex);
                    } else {
                        LOGGER.debug("Order event sent successfully for key={}", key);
                    }
                }
        );
    }
}
