package com.klezovich.camelcoaching.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeciderBean {

    public boolean bodyContainsUsd(CurrencyExchangeController.CurrencyExchange exchange) {
        return exchange.from.contains("USD") || exchange.to.contains("USD");
    }
}
