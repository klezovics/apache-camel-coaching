package com.klezovich.camelcoaching.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFirstRoute extends RouteBuilder {

    private final TimeProvider provider;

    private final ReverseLogger logger;

    @Autowired
    public MyFirstRoute(TimeProvider provider, ReverseLogger logger) {
        this.provider = provider;
        this.logger = logger;
    }

    @Override
    public void configure() throws Exception {
        from("timer:first-timer")
        .log("Initial message body ${body}")
        .bean(provider, "getTime")
        .bean(logger)
        .to("log:first-timer");
    }
}
