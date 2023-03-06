package com.kyc.core.exception.handlers;

import com.kyc.core.exception.KycBatchException;
import com.kyc.core.model.web.MessageData;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;

@AllArgsConstructor
public class KycBatchExceptionHandler implements ExceptionHandler {

    private MessageData messageData;

    @Override
    public void handleException(RepeatContext repeatContext, Throwable throwable) {

        Object inputData = null;
        Exception exc;
        if (throwable instanceof FlatFileParseException) {
            FlatFileParseException exception = (FlatFileParseException) throwable;
            inputData = exception.getInput();
            exc = exception;
        }
        else{
            exc = (Exception) throwable;
        }
        throw KycBatchException.builderBatchException()
                .inputData(inputData)
                .exception(exc)
                .exitStatus(ExitStatus.FAILED)
                .errorData(messageData)
                .build();
    }
}
