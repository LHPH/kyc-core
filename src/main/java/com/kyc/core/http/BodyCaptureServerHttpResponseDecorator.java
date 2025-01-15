package com.kyc.core.http;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class BodyCaptureServerHttpResponseDecorator extends ServerHttpResponseDecorator {

    private static final Logger LOGGER  = LoggerFactory.getLogger(BodyCaptureServerHttpResponseDecorator.class);

    private final DataBufferFactory bufferFactory;
    private final Long startTime;

    public BodyCaptureServerHttpResponseDecorator(ServerHttpResponse delegate, Long startTime) {
        super(delegate);
        this.bufferFactory = delegate.bufferFactory();
        this.startTime = startTime;
    }

    public BodyCaptureServerHttpResponseDecorator(ServerHttpResponse delegate) {
       this(delegate,0L);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

        long currentTime = System.currentTimeMillis();
        Long executeTime = (currentTime - startTime);
        LOGGER.info("Response Finish: {}, Total: {}",currentTime,executeTime);

        HttpStatusCode httpStatusCode = getDelegate().getStatusCode();
        HttpHeaders httpHeaders = getDelegate().getHeaders();
        MediaType contentType = httpHeaders.getContentType();
        long length = httpHeaders.getContentLength();
        LOGGER.info("Response HttpStatus: {}",httpStatusCode);
        LOGGER.info("Response ContentType: {}, Length: {}",contentType,length);
        httpHeaders.forEach((key, value) -> LOGGER.info("Response Headers: Key-> {}, Value -> {}", key, value));

        if(body instanceof Flux<? extends DataBuffer> fluxBody){

            return super.writeWith(fluxBody.map( dataBuffer -> {

                try{
                    byte[] content = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(content);
                    String bodyContent = new String(content, StandardCharsets.UTF_8);
                    LOGGER.info("Response Body: {}", bodyContent);
                    return bufferFactory.wrap(content);
                }
                finally {
                    DataBufferUtils.release(dataBuffer);
                }
            }));
        }
        return super.writeWith(body);
    }
}
