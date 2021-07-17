package com.klezovich.camelcoaching.route.component.activemq;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//Requires ActiveMQ docker container to be running
//Active MQ console is available at http://localhost:8161/admin/queues.jsp
//To run ActiveMQ use docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
@Component
public class ActiveMqProducerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
//        from("timer:my-timer")
//        .transform().constant("My message from ActiveMq")
//        .to("activemq:my-activemq-queue");

        from("file:files/json?noop=true")
        .log("${body}")
        .to("activemq:my-activemq-queue");
    }
}
