package com.tlavu.ordernotificationdemo.notificationservice.repository;

import com.tlavu.ordernotificationdemo.notificationservice.model.NotificationLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationLogRepository extends MongoRepository<NotificationLog, String> {
    List<NotificationLog> findByOrderId(String orderId);
}
