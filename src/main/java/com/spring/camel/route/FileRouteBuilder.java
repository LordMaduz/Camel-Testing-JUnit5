package com.spring.camel.route;

import org.apache.camel.builder.RouteBuilder;

public class FileRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:application?period=5s")
                .log("Timer has been invoked")
                .pollEnrich("file:directory?delete=true")
                .to("file:directory/copy");
    }
}
