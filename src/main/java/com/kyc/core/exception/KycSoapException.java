package com.kyc.core.exception;

import com.kyc.core.model.MessageData;
import lombok.Builder;
import lombok.Getter;

import javax.xml.namespace.QName;

import static com.kyc.core.constants.GeneralConstants.EXC_HTTP_SOAP_FAULT_CODE_LABEL;

@Getter
public class KycSoapException extends KycException{

    private final QName faultCode;

    @Builder(builderMethodName = "builderSoapException")
    public KycSoapException(Object inputData, Object outputData, MessageData errorData, Throwable exception, QName faultCode) {
        super(inputData, outputData, errorData, exception);
        this.faultCode = faultCode;
    }

    @Override
    public String printError(){

        StringBuilder sb = new StringBuilder(super.printError());

        addDescription(sb, EXC_HTTP_SOAP_FAULT_CODE_LABEL,getFaultCode(),true);

        return sb.toString();
    }
}
