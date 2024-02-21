package com.spring.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.core5.http.HttpEntity;
import java.io.*;
import java.util.Objects;

public class FileRouteProcessBuilder extends RouteBuilder {

    private final Long ALLOWED_LENGTH = 25 * 1024L;

    @Override
    public void configure() {
        from("direct:start")
                .process(exchange -> {
                    final File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("application.txt")).toURI());
                    final HttpEntity httpEntity = MultipartEntityBuilder
                            .create()
                            .addBinaryBody("application.txt", file)
                            .build();
                    exchange.getMessage().setHeader(Exchange.FILE_NAME, "application.txt");
                    exchange.getMessage().setHeader(Exchange.CONTENT_TYPE, httpEntity.getContentType());

                    if (httpEntity.getContentLength() < ALLOWED_LENGTH) {
                        exchange.getMessage().setBody(httpEntity.getContent());
                    } else {
                        exchange.getMessage().setBody(inputStream(httpEntity));
                    }
                })
                .to("file:directory/copy");
    }

    private InputStream inputStream(final HttpEntity httpEntity) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        httpEntity.writeTo(byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
