package com.kyc.core.validation.rules;

import com.kyc.core.validation.model.InputData;
import com.kyc.core.validation.model.ResultValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NonNullRuleValidationTest {

    private static final String ERROR_MSG = "ERROR";

    @Test
    public void validate_validateRequiredAndNonNullValue_returnSuccessfulResult(){

        NonNullRuleValidation<Object> validator = new NonNullRuleValidation<>(true,ERROR_MSG);

        InputData<Object> inputData = new InputData<>("TEST");

        ResultValidation result = validator.isValid(inputData);
        Assertions.assertTrue(result.isValid());
        Assertions.assertNotEquals(ERROR_MSG,result.getError());
    }


    @Test
    public void validate_validateNoRequiredAndNonNullValue_returnSuccessfulResult(){

        NonNullRuleValidation<Object> validator = new NonNullRuleValidation<>(false,ERROR_MSG);

        InputData<Object> inputData = new InputData<>("TEST");

        ResultValidation result = validator.isValid(inputData);
        Assertions.assertTrue(result.isValid());
        Assertions.assertNotEquals(ERROR_MSG,result.getError());
    }

    @Test
    public void validate_validateRequiredAndNullValue_returnUnsuccessfulResult(){

        NonNullRuleValidation<Object> validator = new NonNullRuleValidation<>(true,ERROR_MSG);

        InputData<Object> inputData = new InputData<>(null);

        ResultValidation result = validator.isValid(inputData);
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(ERROR_MSG,result.getError());
    }

    @Test
    public void validate_validateRequiredAndNullWrapper_returnUnsuccessfulResult(){

        NonNullRuleValidation<Object> validator = new NonNullRuleValidation<>(true,ERROR_MSG);

        InputData<Object> inputData = null;

        ResultValidation result = validator.isValid(inputData);
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(ERROR_MSG,result.getError());
    }

    @Test
    public void validate_validateNoRequiredAndNullValue_returnSuccessfulResult(){

        NonNullRuleValidation<Object> validator = new NonNullRuleValidation<>(false,ERROR_MSG);

        InputData<Object> inputData = new InputData<>(null);

        ResultValidation result = validator.isValid(inputData);
        Assertions.assertTrue(result.isValid());
        Assertions.assertNotEquals(ERROR_MSG,result.getError());
    }

}
