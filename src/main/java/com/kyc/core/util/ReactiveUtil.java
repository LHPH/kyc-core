package com.kyc.core.util;

import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.MessageData;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

public final class ReactiveUtil {

    public static <T> Mono<T> sendReactiveError(Throwable ex, HttpStatus httpStatus, MessageData messageData, Object inputData){

        return Mono.error(getReactiveError(ex,httpStatus,messageData,inputData));
    }

    public static KycRestException getReactiveError(Throwable ex, HttpStatus httpStatus, MessageData messageData, Object inputData){

        KycRestException restException = KycRestException.builderRestException()
                .errorData(messageData)
                .exception(ex)
                .inputData(inputData)
                .status(ObjectUtils.defaultIfNull(httpStatus,HttpStatus.INTERNAL_SERVER_ERROR))
                .build();
        return restException;
    }

    private ReactiveUtil(){}
}
