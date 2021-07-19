package com.klezovich.camelcoaching.camel;

import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Map;

import static com.klezovich.camelcoaching.camel.CamelTestHelper.getFirstMessageBody;

public class AggregatorTest extends CamelTestSupport {

    @Test
    public void testContentBasedRouter() throws InterruptedException {
        getMockEndpoint("mock:end").expectedMessageCount(1);

        template.sendBodyAndHeaders("direct:start", "a", Map.of("id", 1));
        template.sendBodyAndHeaders("direct:start", "b", Map.of("id", 2));
        template.sendBodyAndHeaders("direct:start", "c", Map.of("id", 3));
        template.sendBodyAndHeaders("direct:start", "d", Map.of("id", 4));

        assertMockEndpointsSatisfied();
        assertEquals("abc", getFirstMessageBody(getMockEndpoint("mock:end")));
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .aggregate(constant(true), AggregationStrategies.bean(new MyBodyAppender(), "append"))
                        .completionSize(3).completionSize(3).id("myAggregator")
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
