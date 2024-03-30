package com.mateusjose98.management.listener;

import com.mateusjose98.management.model.NewUserEvent;
import com.mateusjose98.management.services.EventService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static com.mateusjose98.management.utils.RequestUtils.getDevice;
import static com.mateusjose98.management.utils.RequestUtils.getIpAddress;

@Component
@RequiredArgsConstructor
public class NewUserEventListener {
    private final EventService eventService;
    private final HttpServletRequest request;


    @EventListener
    public void onNewUserEvent(NewUserEvent event) {
        eventService.addUserEvent(event.getEmail(), event.getType(), getIpAddress(request));
    }
}