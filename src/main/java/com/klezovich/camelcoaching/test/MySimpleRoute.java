package com.klezovich.camelcoaching.test;

import org.apache.camel.builder.RouteBuilder;

public class MySimpleRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start")
                .log("${body}")
                .to("mock:output"); // Defines a Mock endpoint called 'output'
    }
}