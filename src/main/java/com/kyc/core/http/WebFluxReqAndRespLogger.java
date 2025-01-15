package com.kyc.core.http;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.Objects;

import static com.kyc.core.constants.GeneralConstants.CORRELATION_ID_HEADER;

//https://gist.github.com/matzegebbe/bf631b2d3ab6d55f58f4b6c1d3511189
public class WebFluxReqAndRespLogger {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final String START_TIME = "start_time";

    protected ServerHttpRequestDecorator logRequest(ServerWebExchange exchange){

        ServerHttpRequest request = exchange.getRequest();

        long startTime = System.currentTimeMillis();
        exchange.getAttributes().put(START_TIME, startTime);

        URI requestURI = request.getURI();
        String scheme = requestURI.getScheme();
        HttpHeaders httpHeaders = request.getHeaders();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        MediaType contentType = httpHeaders.getContentType();
        long length = httpHeaders.getContentLength();

        LOGGER.info("Request Start: {}",startTime);
        LOGGER.info("Request Scheme:{}, Path:{}", scheme, requestURI.getPath());
        LOGGER.info("Request Method:{}, IP:{}, Host:{}", request.getMethod(), request.getRemoteAddress(), requestURI.getHost());
        LOGGER.info("Request ContentType:{}, Content Length:{}", contentType, length);
        httpHeaders.forEach((key, value) -> LOGGER.info("Request Headers: Key-> {}, Value -> {}", key, value));
        queryParams.forEach((key, value) -> LOGGER.info("Request Query Param: Key-> {}, Value-> {}", key, value));

        return new BodyCaptureServerHttpRequestDecorator(request);
    }

    protected ServerHttpResponseDecorator logResponse(ServerWebExchange exchange){

        ServerHttpResponse response = exchange.getResponse();
        Long startTime = ObjectUtils.defaultIfNull(exchange.getAttribute(START_TIME),0L);

        return new BodyCaptureServerHttpResponseDecorator(response,startTime);
    }
}
