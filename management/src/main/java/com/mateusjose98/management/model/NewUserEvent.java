package com.mateusjose98.management.model;


import com.mateusjose98.management.model.enums.EventType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class NewUserEvent extends ApplicationEvent {
    private EventType type;
    private String email;

    public NewUserEvent(String email, EventType type) {
        super(email);
        this.type = type;
        this.email = email;
    }
}
