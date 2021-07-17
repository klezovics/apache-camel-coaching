package com.klezovich.camelcoaching.route.eip;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Splitter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
//        from("timer:t1?period=3000")
//          .routeId("MulticastRoute")
//          .multicast()
//          .to("log:l1", "log:l2", "log:l3");

        from("timer:t1?period=3000")
                .routeId("SplitterRoute")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        var body = "a,b,c\nd,e,f\n,g,h,k";
                        exchange.getIn().setBody(body);
                    }
                })
                .log("Body is ${body}")
                .unmarshal().csv()
                .split(body())
                .log("Msg is ${body}");
    }
}
