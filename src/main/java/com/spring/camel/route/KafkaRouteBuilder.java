package com.spring.camel.route;

import org.apache.camel.builder.RouteBuilder;

import java.io.Serial;

public class KafkaRouteBuilder extends RouteBuilder {

    @Override
    @Serial
    public void configure() throws Exception {

        from("kafka:order-event?brokers=localhost:9092")
                .log("Message received from Kafka : ${body}")
                .process(exchange -> exchange.getIn().setBody(exchange.getMessage().getBody() + " Processed"))
                .to("kafka:inventory-event?brokers=localhost:9092")
                .log("${body}");
    }
}
