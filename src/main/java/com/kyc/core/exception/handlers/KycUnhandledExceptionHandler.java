package com.kyc.core.exception.handlers;

import com.kyc.core.enums.MessageType;
import com.kyc.core.model.MessageData;
import com.kyc.core.model.web.ResponseData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class KycUnhandledExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(KycUnhandledExceptionHandler.class);

    private MessageData messageData;

    public KycUnhandledExceptionHandler(MessageData messageData){
        this.messageData = messageData;
    }

    public KycUnhandledExceptionHandler(){ }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData<Void>> sendErrorResponse(Exception ex){

        ResponseData<Void> response;
        LOGGER.error(" ",ex);

        if(messageData!=null){

            response = ResponseData.<Void>builder()
                    .error(messageData)
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
        else{

            MessageData message = new MessageData("ERROR","ERROR", MessageType.ERROR);
            response = ResponseData.<Void>builder()
                    .error(message)
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
        return response.toResponseEntity();
    }
}
