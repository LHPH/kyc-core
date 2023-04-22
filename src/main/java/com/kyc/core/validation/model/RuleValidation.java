package com.kyc.core.validation.model;

public interface RuleValidation<T> {

    void setRequired(boolean required);

    boolean isRequired();

    ResultValidation isValid(InputData<T> inputData);
}
