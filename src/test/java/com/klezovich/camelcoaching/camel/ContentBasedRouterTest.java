package com.klezovich.camelcoaching.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Map;

public class ContentBasedRouterTest extends CamelTestSupport {

    @Test
    public void testContentBasedRouter() throws InterruptedException {
        getMockEndpoint("mock:xml").expectedMessageCount(2);
        getMockEndpoint("mock:json").expectedMessageCount(1);
        getMockEndpoint("mock:other").expectedMessageCount(1);

        template.sendBodyAndHeaders("direct:start", "", Map.of("type", "xml"));
        template.sendBodyAndHeaders("direct:start", "", Map.of("type", "xml"));
        template.sendBodyAndHeaders("direct:start", "", Map.of("type", "json"));
        template.sendBodyAndHeaders("direct:start", "", Map.of("type", "none"));

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                    .choice()
                    .when(header("type").isEqualTo("xml"))
                    .to("mock:xml")
                    .when(header("type").isEqualTo("json"))
                    .to("mock:json")
                    .otherwise()
                    .to("mock:other");
            }
        };
    }
}
