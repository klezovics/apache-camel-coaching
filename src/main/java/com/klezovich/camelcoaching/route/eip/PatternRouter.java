package com.klezovich.camelcoaching.route.eip;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;

//@Component
public class PatternRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
//        from("timer:t1?period=3000")
//          .routeId("MulticastRoute")
//          .multicast()
//          .to("log:l1", "log:l2", "log:l3");

        from("timer:t1?period=3000")
                .routeId("ConditionalRoute")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        var body = String.valueOf(new Random().nextInt());
                        exchange.getIn().setBody(body);
                    }
                })
                .log("Body is ${body}")
                .choice()
                  .when(simple("${body} > 0"))
                  .to("direct:positive")
                .endChoice()
                .otherwise()
                  .to("direct:negative")
                .end();

        from("direct:negative")
        .log("Value is negative");

        from("direct:positive")
        .log("Value is positive");
    }
}
