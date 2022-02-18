package com.kyc.core.exception;

import com.kyc.core.model.web.FieldErrorData;
import com.kyc.core.model.web.MessageData;
import com.kyc.core.model.web.MessageFieldErrorData;
import com.kyc.core.model.web.ResponseData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE-1)
public class KycValidationRestExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(KycValidationRestExceptionHandler.class);

    private final MessageData messageData;

    public KycValidationRestExceptionHandler(MessageData messageData){
        this.messageData = messageData;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData<Void>> handleBadRequest(MethodArgumentNotValidException ex){

        LOGGER.error(" ",ex);

        MessageFieldErrorData messageFieldData = new MessageFieldErrorData();
        messageFieldData.setCode(messageData.getCode());
        messageFieldData.setMessage(messageData.getMessage());
        messageFieldData.setType(messageData.getType());

        for(FieldError error : ex.getBindingResult().getFieldErrors()){

            FieldErrorData fieldErrorData = FieldErrorData.builder()
                    .field(error.getField())
                    .value(String.valueOf(error.getRejectedValue()))
                    .message(error.getDefaultMessage())
                    .build();

            messageFieldData.addDetail(fieldErrorData);
        }

        return ResponseData.<Void>of(messageFieldData, HttpStatus.BAD_REQUEST)
                .toResponseEntity();
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ResponseData<Void>> handleBadRequest(MissingRequestHeaderException ex){

        LOGGER.error(" ",ex);
        MessageData messageToSent = MessageData.copy(messageData);
        messageToSent.setMessage(ex.getMessage());

        return ResponseData.<Void>of(messageToSent, HttpStatus.BAD_REQUEST)
                .toResponseEntity();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseData<Void>> handleBadRequest(MissingServletRequestParameterException ex){

        LOGGER.error(" ",ex);
        MessageData messageToSent = MessageData.copy(messageData);
        messageToSent.setMessage(ex.getMessage());

        return ResponseData.<Void>of(messageToSent, HttpStatus.BAD_REQUEST)
                .toResponseEntity();
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ResponseData<Void>> handleBadRequest(MissingPathVariableException ex){

        LOGGER.error(" ",ex);

        MessageData messageToSent = MessageData.copy(messageData);
        messageToSent.setMessage(ex.getMessage());

        return ResponseData.<Void>of(messageToSent, HttpStatus.BAD_REQUEST)
                .toResponseEntity();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseData<Void>> handleBadRequest(HttpMessageNotReadableException ex){

        LOGGER.error(" ",ex);
        MessageData messageToSent = MessageData.copy(messageData);
        messageToSent.setMessage("Request Body JSON Invalid");

        return ResponseData.<Void>of(messageToSent, HttpStatus.BAD_REQUEST)
                .toResponseEntity();
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ResponseData<Void>> handleBadRequest(TypeMismatchException ex){

        LOGGER.error(" ",ex);
        MessageData messageToSent = MessageData.copy(messageData);
        messageToSent.setMessage(ex.getMessage());

        return ResponseData.<Void>of(messageToSent, HttpStatus.BAD_REQUEST)
                .toResponseEntity();
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity<ResponseData<Void>> handleBadRequest(UnsatisfiedServletRequestParameterException ex){

        LOGGER.error(" ",ex);
        MessageData messageToSent = MessageData.copy(messageData);
        messageToSent.setMessage(ex.getMessage());

        return ResponseData.<Void>of(messageToSent,HttpStatus.BAD_REQUEST)
                .toResponseEntity();
    }


}
