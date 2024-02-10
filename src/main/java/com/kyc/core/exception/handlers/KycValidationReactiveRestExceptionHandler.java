package com.kyc.core.exception.handlers;

import com.kyc.core.model.web.FieldErrorData;
import com.kyc.core.model.MessageData;
import com.kyc.core.model.web.MessageFieldErrorData;
import com.kyc.core.model.web.ResponseData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE-1)
public class KycValidationReactiveRestExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(KycValidationReactiveRestExceptionHandler.class);

    private final MessageData messageData;

    public KycValidationReactiveRestExceptionHandler(MessageData messageData){
        this.messageData = messageData;
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ResponseData<Void>> handleBadRequest(WebExchangeBindException ex){

        LOGGER.error(" ",ex);

        MessageFieldErrorData messageFieldData = new MessageFieldErrorData();
        messageFieldData.setCode(messageData.getCode());
        messageFieldData.setMessage(messageData.getMessage());
        messageFieldData.setType(messageData.getType());

        List<FieldErrorData> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    return FieldErrorData.builder()
                            .field(error.getField())
                            .message(error.getDefaultMessage())
                            .value(String.valueOf(error.getRejectedValue()))
                            .build();
                }).collect(Collectors.toList());
        messageFieldData.setDetails(errors);

        return ResponseData.<Void>of(messageFieldData, HttpStatus.BAD_REQUEST)
                .toResponseEntity();
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ResponseData<Void>> handleBadRequest(ServerWebInputException ex){

        LOGGER.error(" ",ex);
        MessageData messageToSent = MessageData.copy(messageData);
        messageToSent.setMessage(ex.getReason());

        return ResponseData.<Void>of(messageToSent, HttpStatus.BAD_REQUEST)
                .toResponseEntity();
    }

}
