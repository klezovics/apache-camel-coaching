package com.klezovich.camelcoaching.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReverseLogger {

    void reverse(String message) {
        log.info("{}",new StringBuilder(message).reverse());
    }
}
