package com.klezovich.camelcoaching.route.component.activemq;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//Requires ActiveMQ docker container to be running
@Component
public class ActiveMqConsumerRoute extends RouteBuilder {
    @Override

    public void configure() throws Exception {

        from("activemq:my-activemq-queue")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        var msg = exchange.getIn().getBody().toString();
                        msg = new StringBuilder(msg).reverse().toString();
                        exchange.getIn().setBody(msg);
                    }
                })
                .to("log:my-log");

    }
}
