package com.klezovich.camelcoaching.route.component;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class FileProcessingRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:files/input")
        .log("${body}")
        .to("file:files/output");
    }
}
