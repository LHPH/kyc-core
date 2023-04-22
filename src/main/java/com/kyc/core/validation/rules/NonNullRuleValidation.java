package com.kyc.core.validation.rules;

import com.kyc.core.validation.model.InputData;
import com.kyc.core.validation.model.ResultValidation;

public class NonNullRuleValidation<T> extends AbstractRuleValidation<T>{

    public NonNullRuleValidation(boolean required, String message){
        super(required,message);
    }

    @Override
    public ResultValidation validate(InputData<T> inputData) {

        if(inputData.getData()!=null){

            return ResultValidation.builder()
                    .valid(true)
                    .build();
        }
        return ResultValidation.builder()
                .valid(false)
                .field(inputData.getFieldName())
                .error(getErrorMessage())
                .build();
    }
}
