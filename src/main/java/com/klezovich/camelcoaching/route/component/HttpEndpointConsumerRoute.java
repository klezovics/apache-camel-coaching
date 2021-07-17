package com.klezovich.camelcoaching.route.component;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class HttpEndpointConsumerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:start")
        .to("http://swapi.dev/api/planets/1/")
                .log("Message body ${body}")
                .to("log:first-timer");
    }
}

