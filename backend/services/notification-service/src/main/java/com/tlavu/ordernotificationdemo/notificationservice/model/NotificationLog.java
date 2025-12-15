package com.tlavu.ordernotificationdemo.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "notification_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationLog {

    @Id
    private String id;

    @Field("order_id")
    private String orderId;

    private String message;
    private NotificationType type;
    private NotificationStatus status;
    private Instant sentAt;
}
