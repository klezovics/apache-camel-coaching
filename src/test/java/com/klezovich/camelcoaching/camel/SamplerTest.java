package com.klezovich.camelcoaching.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class SamplerTest extends CamelTestSupport {

    @Test
    public void testContentBasedRouter() throws InterruptedException {
        getMockEndpoint("mock:end").expectedMessageCount(1);

        for (int i = 0; i < 1000; i++) {
            template.sendBody("direct:start", "a");
        }

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                    .sample(1000)
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
