package com.spring.camel.route;

import org.apache.camel.builder.RouteBuilder;

public class LogRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start")
                .log("${body}")
                .to("mock:result");
    }
}
