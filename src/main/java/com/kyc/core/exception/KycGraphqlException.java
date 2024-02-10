package com.kyc.core.exception;


import com.kyc.core.model.MessageData;
import graphql.ErrorClassification;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static com.kyc.core.constants.GeneralConstants.EXC_GRAPHQL_ERROR_TYPE;

@Getter
public class KycGraphqlException extends KycException {

    private ErrorClassification errorType;

    @Builder(builderMethodName = "builderGraphqlException")
    public KycGraphqlException(Object inputData, Object outputData, MessageData errorData, Throwable exception, ErrorClassification errorType) {
        super(inputData, outputData, errorData, exception);
        this.errorType = errorType;
    }

    public Map<String,Object> getExtensions(){

        MessageData messageData = getErrorData();
        Map<String,Object> map = new HashMap<>();
        if(messageData!=null){

            map.put("code",messageData.getCode());
            map.put("description",messageData.getMessage());
            map.put("time",messageData.getTime());
            return map;
        }
        return map;
    }

    @Override
    public String printError() {

        StringBuilder sb = new StringBuilder(super.printError());

        addDescription(sb,EXC_GRAPHQL_ERROR_TYPE,getErrorType(),true);

        return sb.toString();
    }


}
