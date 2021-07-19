package com.klezovich.camelcoaching.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Map;

public class FilterTest extends CamelTestSupport {

    @Test
    public void testContentBasedRouter() throws InterruptedException {
        getMockEndpoint("mock:end").expectedMessageCount(1);

        template.sendBodyAndHeaders("direct:start", "", Map.of("pass_filter", "true"));
        template.sendBodyAndHeaders("direct:start", "", Map.of());
        template.sendBodyAndHeaders("direct:start", "", Map.of("pass_filter", "false"));

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                    .filter(header("pass_filter").isEqualTo("true"))
                    .to("mock:end");
            }
        };
    }
}
