package com.kyc.core.exception;


import com.kyc.core.model.web.MessageData;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.kyc.core.constants.GeneralConstants.EXC_HTTP_STATUS_LABEL;

@Getter
public class KycRestException extends KycException{

    private final HttpStatus status;

    @Builder(builderMethodName = "builderRestException")
    public KycRestException(Object inputData, Object outputData, MessageData errorData, Throwable exception, HttpStatus status) {
        super(inputData, outputData, errorData, exception);
        this.status = status;
    }

    @Override
    public String printError(){

        StringBuilder sb = new StringBuilder(super.printError());

        addDescription(sb, EXC_HTTP_STATUS_LABEL,getStatus(),true);

        return sb.toString();
    }
}
