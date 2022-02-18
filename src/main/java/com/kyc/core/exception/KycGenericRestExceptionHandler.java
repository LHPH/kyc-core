package com.kyc.core.exception;


import com.kyc.core.model.web.ResponseData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE-1)
public class KycGenericRestExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(KycGenericRestExceptionHandler.class);

    @ExceptionHandler(KycRestException.class)
    public ResponseEntity<ResponseData<Void>> sendErrorResponse(KycRestException ex){

        LOGGER.error("{}",ex.toString());

        ResponseData<Void> response = ResponseData.<Void>builder()
                .error(ex.getErrorData())
                .httpStatus(ex.getStatus())
                .build();

        return response.toResponseEntity();
    }

}
