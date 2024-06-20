package com.WalkiePaw.domain.notification.Repository;

import com.WalkiePaw.domain.notification.entity.Notification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Notification notification) {
        em.persist(notification);
    }
}

