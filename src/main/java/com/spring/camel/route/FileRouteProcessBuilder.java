package com.spring.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.*;
import java.util.Objects;

public class FileRouteProcessBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start")
                .process(exchange -> {
                    final File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("application.txt")).toURI());
                    final HttpEntity httpEntity = MultipartEntityBuilder
                            .create()
                            .addBinaryBody("application.txt", file)
                            .build();
                    exchange.getMessage().setHeader(Exchange.FILE_NAME, "application.txt");
                    exchange.getMessage().setHeader(Exchange.CONTENT_TYPE, httpEntity.getContentType().getValue());
                    exchange.getMessage().setBody(httpEntity.getContent());

                })
                .to("file:directory/copy");
    }

    private InputStream inputStream(final HttpEntity httpEntity) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        httpEntity.writeTo(byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
