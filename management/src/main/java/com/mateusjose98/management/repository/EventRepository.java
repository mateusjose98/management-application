package com.mateusjose98.management.repository;



import com.mateusjose98.management.model.UserEvent;
import com.mateusjose98.management.model.enums.EventType;

import java.util.Collection;


public interface EventRepository {
    Collection<UserEvent> getEventsByUserId(Long userId);
    void addUserEvent(String email, EventType eventType, String device, String ipAddress);
    void addUserEvent(Long userId, EventType eventType, String device, String ipAddress);
}
