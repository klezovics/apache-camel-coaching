package com.klezovich.camelcoaching.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class RecipientListTest extends CamelTestSupport {

    @Test
    public void testContentBasedRouter() throws InterruptedException {
        getMockEndpoint("mock:xml").expectedMessageCount(1);
        getMockEndpoint("mock:json").expectedMessageCount(1);
        getMockEndpoint("mock:other").expectedMessageCount(1);
        getMockEndpoint("mock:fake").expectedMessageCount(0);

        template.sendBodyAndHeaders(
            "direct:start",
            "",
            Map.of("recepients", List.of("mock:xml", "mock:json", "mock:other"))
        );

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                    .recipientList(header("recepients"));
            }
        };
    }
}
