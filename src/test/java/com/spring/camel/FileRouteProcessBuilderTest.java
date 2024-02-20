package com.spring.camel;

import com.spring.camel.route.FileRouteProcessBuilder;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class FileRouteProcessBuilderTest extends CamelTestSupport {


    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new FileRouteProcessBuilder();
    }


    @Test
    public void testFileRoute() throws InterruptedException {
        template.requestBody("direct:start", "Test From Message");
        Thread.sleep(5000);
    }

}
