package com.klezovich.camelcoaching.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.Matchers.contains;

public class MyRouteTest extends CamelTestSupport {

    @Test
    public void testSendingDataToMockEndpoint() throws InterruptedException {
        template.sendBody("direct:start", "This is the body");
        getMockEndpoint("mock:end").expectedMessageCount(1);
        getMockEndpoint("mock:xml").expectedMessageCount(0);
        assertMockEndpointsSatisfied();
    }

    @Test
    public void testCorrectMessageBodyIsSent() {
        template.sendBody("direct:start", "MyBody");
        getMockEndpoint("mock:end").expectedMessageCount(1);
        assertEquals("MyBody", getFirstMessageBody("mock:end"));
    }

    @Test
    public void testCorrectMessageHeadersAreSent() {
        template.sendBodyAndHeaders("direct:start", "", Map.of("id", "100"));

        getMockEndpoint("mock:end").expectedMessageCount(1);
        var headers = getMockEndpoint("mock:end").getExchanges().get(0).getMessage().getHeaders();
        assertEquals(1, headers.keySet().size());
        MatcherAssert.assertThat(headers.keySet(), contains("id"));
        MatcherAssert.assertThat(headers.values(), contains("100"));
    }

    private Map getFirstMessageHeaders(String endpointUri) {
        return getMockEndpoint(endpointUri)
            .getReceivedExchanges()
            .get(0)
            .getIn()
            .getHeaders();
    }

    private String getFirstMessageBody(String endpointUri) {
        return getMockEndpoint(endpointUri)
            .getReceivedExchanges()
            .get(0)
            .getIn()
            .getBody(String.class);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                    // format inhouse to csv using a bean
                    // and save it to a file
                    .to("mock:end");
            }
        };
    }
}
