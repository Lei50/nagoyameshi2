package com.example.nagoyameshi.event;

import org.springframework.context.ApplicationEvent;

import com.example.nagoyameshi.entity.User;

import lombok.Getter;

@Getter
public class PasswordResetEvent extends ApplicationEvent {
    private final User user;
    private final String resetUrl;

    public PasswordResetEvent(Object source, User user, String resetUrl) {
        super(source);
        this.user = user;
        this.resetUrl = resetUrl;
    }
}