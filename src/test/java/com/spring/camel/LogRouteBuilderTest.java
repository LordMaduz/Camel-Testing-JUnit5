package com.spring.camel;

import com.spring.camel.route.LogRouteBuilder;
import org.apache.camel.EndpointInject;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LogRouteBuilderTest extends CamelTestSupport {

    @EndpointInject("mock:result")
    private MockEndpoint mockEndpoint;


    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new LogRouteBuilder();
    }

    @Test
    public void test() throws InterruptedException {
        final String message = "Hello World !";
        mockEndpoint.expectedBodiesReceived(message);
        final  String output = template.requestBody("direct:start", message, String.class);
        Assertions.assertEquals(message, output);
        mockEndpoint.assertIsSatisfied();
    }
}
