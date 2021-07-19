package com.klezovich.camelcoaching.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class SplitterTest extends CamelTestSupport {

    @Test
    public void testContentBasedRouter() throws InterruptedException {
        getMockEndpoint("mock:end").expectedMessageCount(3);

        template.sendBody("direct:start", "a\nb\nc\n");

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                    .split(body().tokenize("\n"))
                    .to("mock:end");
            }
        };
    }
}
