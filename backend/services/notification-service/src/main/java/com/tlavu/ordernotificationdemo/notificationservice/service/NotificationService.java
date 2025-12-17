package com.tlavu.ordernotificationdemo.notificationservice.service;

import com.tlavu.ordernotificationdemo.notificationservice.dto.OrderEventDTO;
import com.tlavu.ordernotificationdemo.notificationservice.model.NotificationLog;

import java.util.List;

public interface NotificationService {

    // Command to process an order event and send notification
    void processOrderEvent(OrderEventDTO event);
    boolean sendNotification(NotificationLog logEntry);
    NotificationLog saveNotificationLog(NotificationLog logEntry);

    // Query methods could be added here if needed
    List<NotificationLog> findAll();
    List<NotificationLog> findByOrderId(String orderId);
}

