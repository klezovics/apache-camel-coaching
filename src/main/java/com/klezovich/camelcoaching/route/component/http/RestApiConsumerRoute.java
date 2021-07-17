package com.klezovich.camelcoaching.route.component.http;

import com.klezovich.camelcoaching.route.component.activemq.UserBeanProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

//Requires ActiveMQ docker container to be running
//@Component
public class RestApiConsumerRoute extends RouteBuilder {

    @Autowired
    private UserBeanProcessor processor;

    @Autowired
    private DeciderBean decider;

    private final List<String> currencyList = Arrays.asList(new String[]{"USD", "EUR", "RUR"});

    @Override
    public void configure() throws Exception {

        restConfiguration().host("localhost").port(8080);

        from("timer:rest-api-consumer?period=2000")
        .routeId("HttpRestApiRoute")
        .setHeader("from", this::getRandomCurrency)
        .setHeader( "to", this::getRandomCurrency)
        .to("rest:get:/api/from/${from}/to/${to}")
        .unmarshal().json(JsonLibrary.Jackson, CurrencyExchangeController.CurrencyExchange.class)
        .choice()
            .when(simple("${body.value} > 0"))
              .log("Positive price")
            .endChoice()
            .otherwise()
               .choice()
                  .when(method(decider, "bodyContainsUsd"))
                     .log("$$$$$$$$$$$$$$$$$$$$")
                  .endChoice()
               .end()
              .log("Negative price")
            .endChoice()
        .end()
        .to("direct:log-value");

        //Cool ... a nice reusable route
        from("direct:log-value")
          .log("${body.value} from ${body.from} to ${body.to}");
    }

    private String getRandomCurrency() {
        return currencyList.get(new Random().nextInt(currencyList.size()));
    }
}
