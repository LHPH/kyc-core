package com.kyc.core.validation.rules;

import com.kyc.core.validation.model.InputData;
import com.kyc.core.validation.model.ResultValidation;
import com.kyc.core.validation.model.RuleValidation;

import java.util.Objects;

public abstract class AbstractRuleValidation<T> implements RuleValidation<T> {

    private boolean required;
    private String errorMessage;

    public AbstractRuleValidation(boolean required){
        this(required,null);
    }

    public AbstractRuleValidation(boolean required, String errorMessage){
        this.required = required;
        this.errorMessage = errorMessage;
    }

    public boolean evaluate(InputData<T> inputData){
        return required || !Objects.isNull(inputData.getData());
    }

    @Override
    public ResultValidation isValid(InputData<T> inputData) {

        if (evaluate(inputData)) {
            return validate(inputData);
        }
        return ResultValidation.builder()
                .valid(true)
                .build();
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public abstract ResultValidation validate(InputData<T> inputData);
}
