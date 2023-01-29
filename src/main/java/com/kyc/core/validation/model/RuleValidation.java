package com.kyc.core.validation.model;

public interface RuleValidation<T> {


    ResultValidation isValid(InputData<T> inputData);
}
