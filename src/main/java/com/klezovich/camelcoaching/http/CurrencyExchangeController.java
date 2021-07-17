package com.klezovich.camelcoaching.http;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class CurrencyExchangeController {

    @GetMapping("/from/{fromCurrency}/to/{toCurrency}")
    public CurrencyExchange findConversionValue(
            @PathVariable String fromCurrency,
            @PathVariable String toCurrency
    ) {
        return CurrencyExchange
                .builder()
                .from(fromCurrency)
                .to(toCurrency)
                .value(new Random().nextInt()).build();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CurrencyExchange {
        int value;
        String from;
        String to;
    }
}



