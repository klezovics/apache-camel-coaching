package com.klezovich.camelcoaching.test;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class MySimpleRouteTest extends CamelTestSupport {

    // We override this method with the routes to be tested
    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        // We override this method and provide our RouteBuilder class
        return new MySimpleRoute();
    }

    // We write a simple JUnit test case
    @Test
    public void testRoute() throws Exception {
        // 1. Arrange.. get the mock endpoint and set an assertion
        MockEndpoint mock = getMockEndpoint("mock:output");
        MockEndpoint start = getMockEndpoint("mock:direct:start");

        mock.expectedMessageCount(2);
        mock.expectedBodiesReceived("ABC","CBA");

        start.expectedMessageCount(2);
        start.expectedBodiesReceived("ABC","CBA");


        // 2. Act.. send a message to the start component
        template.sendBody("direct:start", "ABC");
        template.sendBody("direct:start", "CBA");

        // 3. Assert.. verify that the mock component received 1 message
        mock.assertIsSatisfied();
        start.assertIsSatisfied();
    }

    @Override
    public String isMockEndpoints() {
        return "*";
    }


}