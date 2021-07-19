package com.klezovich.camelcoaching.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class MulticastTest extends CamelTestSupport {

    @Test
    public void testContentBasedRouter() throws InterruptedException {
        getMockEndpoint("mock:end").expectedMessageCount(1);
        getMockEndpoint("mock:end1").expectedMessageCount(1);
        getMockEndpoint("mock:final").expectedMessageCount(1);

        template.sendBody("direct:start", "a");

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                    .multicast().to("mock:end", "mock:end1")
                    .end()
                    .to("mock:final");
            }
        };
    }

    public class MyBodyAppender {

        public String append(String existing, String next) {
            return existing + next;
        }
    }
}
