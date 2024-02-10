package com.kyc.core.exception.handlers;

import com.kyc.core.model.MessageData;
import com.kyc.core.model.web.ResponseData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE-1)
public class KycRestAuthValidationExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(KycRestAuthValidationExceptionHandler.class);

    private final MessageData messageData;

    public KycRestAuthValidationExceptionHandler(MessageData messageData){
        this.messageData = messageData;
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<ResponseData<Void>> handleOAuth2AuthenticationException(OAuth2AuthenticationException ex){

        LOGGER.error(" ",ex);
        return ResponseData.<Void>of(MessageData.copy(messageData), HttpStatus.UNAUTHORIZED)
                .toResponseEntity();
    }
}
