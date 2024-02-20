package com.spring.camel;

import com.spring.camel.route.FileRouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class FileRouteBuilderTest extends CamelTestSupport {


    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new FileRouteBuilder();
    }


    @Test
    public void testFileRoute() throws InterruptedException {
        template.requestBodyAndHeader("file:directory?delete=true", "Hello", Exchange.FILE_NAME, "application.txt");
        Thread.sleep(5000);
    }

}
