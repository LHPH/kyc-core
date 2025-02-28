package com.kyc.core.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.MessageData;
import com.kyc.core.model.web.ResponseData;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.OutputStream;

public class BearerTokenAuthenticationEntryPointDelegate implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(BearerTokenAuthenticationEntryPointDelegate.class);

    private final AuthenticationEntryPoint entryPoint;
    private final ObjectMapper objectMapper;
    private final MessageData messageData;

    public BearerTokenAuthenticationEntryPointDelegate(MessageData messageData){
        this(messageData,new ObjectMapper());
    }

    public BearerTokenAuthenticationEntryPointDelegate(MessageData messageData, ObjectMapper objectMapper){
        this(messageData,objectMapper,new BearerTokenAuthenticationEntryPoint());
    }

    public BearerTokenAuthenticationEntryPointDelegate(MessageData messageData, ObjectMapper objectMapper, AuthenticationEntryPoint authenticationEntryPoint){
        this.objectMapper = objectMapper;
        this.entryPoint = authenticationEntryPoint;
        this.messageData = messageData;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        entryPoint.commence(request,response,authException);

        OutputStream responseStream = response.getOutputStream();
        LOGGER.error(" ",authException);
        if(authException instanceof InvalidBearerTokenException invalidBearerTokenException){

            BadJwtException badJwtException = (BadJwtException) invalidBearerTokenException.getCause();

            if(badJwtException.getCause() instanceof KycRestException kycRestException){

                response.setStatus(kycRestException.getStatus().value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                ResponseData<Void> customResponse = ResponseData.of(kycRestException.getErrorData(),kycRestException.getStatus());
                objectMapper.writeValue(responseStream,customResponse);
            }
        }
        else{

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ResponseData<Void> customResponse = ResponseData.of(messageData, HttpStatus.UNAUTHORIZED);
            objectMapper.writeValue(responseStream,customResponse);
        }
    }
}
