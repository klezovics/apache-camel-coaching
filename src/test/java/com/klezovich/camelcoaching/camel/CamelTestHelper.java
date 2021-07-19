package com.klezovich.camelcoaching.camel;

import org.apache.camel.component.mock.MockEndpoint;

import java.util.Map;

public class CamelTestHelper {

    public static Map getFirstMessageHeaders(MockEndpoint endpoint) {
        return getNthMessageHeaders(endpoint, 0);
    }

    public static String getFirstMessageBody(MockEndpoint endpoint) {
        return getNthMessageBody(endpoint, 0);
    }

    public static Map getNthMessageHeaders(MockEndpoint endpoint, int index) {
        return endpoint
            .getReceivedExchanges()
            .get(index)
            .getIn()
            .getHeaders();
    }

    public static String getNthMessageBody(MockEndpoint endpoint, int index) {
        return endpoint
            .getReceivedExchanges()
            .get(index)
            .getIn()
            .getBody(String.class);
    }
}
