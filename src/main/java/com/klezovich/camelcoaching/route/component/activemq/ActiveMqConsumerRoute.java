package com.klezovich.camelcoaching.route.component.activemq;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//Requires ActiveMQ docker container to be running
//@Component
public class ActiveMqConsumerRoute extends RouteBuilder {

    @Autowired
    private UserBeanProcessor processor;

    @Override
    public void configure() throws Exception {

        from("activemq:my-activemq-queue")
        .unmarshal().json(JsonLibrary.Jackson, UserBean.class)
        .bean(processor)
        .to("log:my-log");

    }
}
