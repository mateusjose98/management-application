package com.mateusjose98.management.services;


import com.mateusjose98.management.model.UserEvent;
import com.mateusjose98.management.model.enums.EventType;
import com.mateusjose98.management.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;



@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Collection<UserEvent> getEventsByUserId(Long userId) {
        return eventRepository.getEventsByUserId(userId);
    }


    public void addUserEvent(String email, EventType eventType, String device, String ipAddress) {
        eventRepository.addUserEvent(email, eventType, device, ipAddress);
    }


    public void addUserEvent(Long userId, EventType eventType, String device, String ipAddress) {

    }
}
