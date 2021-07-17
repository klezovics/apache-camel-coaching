package com.klezovich.camelcoaching.route;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeProvider {

    public String getTime() {
        return LocalDateTime.now().toString();
    }
}
