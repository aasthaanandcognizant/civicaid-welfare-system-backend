package com.cognizant.civicaid.repository;

import com.cognizant.civicaid.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findByUser_UserId(Long userId, Pageable pageable);
    Page<Notification> findByUser_UserIdAndStatus(Long userId, Notification.NotificationStatus status, Pageable pageable);
    long countByUser_UserIdAndStatus(Long userId, Notification.NotificationStatus status);

    @Modifying
    @Query("UPDATE Notification n SET n.status = 'READ', n.readAt = CURRENT_TIMESTAMP WHERE n.user.userId = :userId AND n.status = 'UNREAD'")
    int markAllAsReadByUser(Long userId);
}
