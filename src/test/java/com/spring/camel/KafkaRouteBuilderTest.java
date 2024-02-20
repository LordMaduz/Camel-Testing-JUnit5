package com.spring.camel;

import com.spring.camel.route.FileRouteBuilder;
import com.spring.camel.route.KafkaRouteBuilder;
import org.apache.camel.EndpointInject;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class KafkaRouteBuilderTest extends CamelTestSupport {

    @EndpointInject("mock:result")
    private MockEndpoint mockEndpoint;

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new KafkaRouteBuilder();
    }


    @Test
    public void test() throws Exception {

        AdviceWith.adviceWith(0, context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:start");
                interceptSendToEndpoint("kafka:inventory-event?brokers=localhost:9092")
                        .skipSendToOriginalEndpoint()
                        .to("mock:result");
            }
        });

        final String message = "New Order";
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedBodiesReceived("New Order Processed");
        template.sendBody("direct:start", message);
        mockEndpoint.assertIsSatisfied();

    }
}
