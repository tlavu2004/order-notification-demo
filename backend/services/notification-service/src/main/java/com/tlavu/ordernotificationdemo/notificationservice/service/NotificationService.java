package com.tlavu.ordernotificationdemo.notificationservice.service;

import com.tlavu.ordernotificationdemo.notificationservice.dto.OrderEventDTO;
import com.tlavu.ordernotificationdemo.notificationservice.model.NotificationLog;

public interface NotificationService {
    void processOrderEvent(OrderEventDTO event);

    boolean sendNotification(NotificationLog logEntry);

    NotificationLog saveNotificationLog(NotificationLog logEntry);
}

