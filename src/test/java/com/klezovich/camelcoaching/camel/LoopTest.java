package com.klezovich.camelcoaching.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Map;

public class LoopTest extends CamelTestSupport {

    @Test
    public void testContentBasedRouter() throws InterruptedException {
        getMockEndpoint("mock:end").expectedMessageCount(3);

        template.sendBodyAndHeaders("direct:start", "a", Map.of("loop", 3));

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                    .loop(header("loop"))
                    .to("mock:end");
            }
        };
    }

    public class MyBodyAppender {

        public String append(String existing, String next) {
            return existing + next;
        }
    }
}
