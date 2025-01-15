package com.kyc.core.http;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayOutputStream;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.kyc.core.constants.GeneralConstants.CORRELATION_ID_HEADER;

public class BodyCaptureServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    private static final Logger LOGGER  = LoggerFactory.getLogger(BodyCaptureServerHttpRequestDecorator.class);

    public BodyCaptureServerHttpRequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
    }

    @Override
    public Flux<DataBuffer> getBody() {

        return super.getBody()
                /*.publishOn(Schedulers.boundedElastic())*/.doOnNext(dataBuffer -> {

                    String requestBody = dataBuffer.toString(StandardCharsets.UTF_8);
                    LOGGER.info("Request Body: {}",requestBody);

                   /* try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                        Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
                        String requestBody = IOUtils.toString(baos.toByteArray(), StandardCharsets.UTF_8.toString());//MODIFY REQUEST and Return the Modified request
                        LOGGER.info("Request Body: {}",requestBody);
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                    }*/
                });
    }
}
