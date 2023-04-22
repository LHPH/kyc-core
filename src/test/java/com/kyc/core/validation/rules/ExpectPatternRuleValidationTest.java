package com.kyc.core.validation.rules;

import com.kyc.core.validation.model.InputData;
import com.kyc.core.validation.model.ResultValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExpectPatternRuleValidationTest {

    private static final String ERROR_MSG = "ERROR";
    private static final String REGEX = "^TEST$";

    @Test
    public void validate_validateRequiredAndExpectedValue_returnSuccessfulResult(){

        ExpectPatternRuleValidation validator = new ExpectPatternRuleValidation(REGEX,true,ERROR_MSG);

        InputData<String> inputData = new InputData<>("TEST");

        ResultValidation result = validator.isValid(inputData);
        Assertions.assertTrue(result.isValid());
        Assertions.assertNotEquals(ERROR_MSG,result.getError());
    }


    @Test
    public void validate_validateNoRequiredAndExpectedValue_returnSuccessfulResult(){

        ExpectPatternRuleValidation validator = new ExpectPatternRuleValidation(REGEX,false,ERROR_MSG);

        InputData<String> inputData = new InputData<>("TEST");

        ResultValidation result = validator.isValid(inputData);
        Assertions.assertTrue(result.isValid());
        Assertions.assertNotEquals(ERROR_MSG,result.getError());
    }

    @Test
    public void validate_validateRequiredAndNullValue_returnUnsuccessfulResult(){

        ExpectPatternRuleValidation validator = new ExpectPatternRuleValidation(REGEX,true,ERROR_MSG);

        InputData<String> inputData = new InputData<>(null);

        ResultValidation result = validator.isValid(inputData);
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(ERROR_MSG+": null",result.getError());
    }

    @Test
    public void validate_validateRequiredAndNullWrapper_returnUnsuccessfulResult(){

        ExpectPatternRuleValidation validator = new ExpectPatternRuleValidation(REGEX,true,ERROR_MSG);

        InputData<String> inputData = null;

        ResultValidation result = validator.isValid(inputData);
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(ERROR_MSG,result.getError());
    }

    @Test
    public void validate_validateRequiredAndBadExpectedValue_returnUnsuccessfulResult(){

        ExpectPatternRuleValidation validator = new ExpectPatternRuleValidation(REGEX,true,ERROR_MSG);

        InputData<String> inputData = new InputData<>("TEST2");

        ResultValidation result = validator.isValid(inputData);
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(ERROR_MSG+": TEST2",result.getError());
    }

    @Test
    public void validate_validateNoRequiredAndBadExpectedValue_returnUnsuccessfulResult(){

        ExpectPatternRuleValidation validator = new ExpectPatternRuleValidation(REGEX,false,ERROR_MSG);

        InputData<String> inputData = new InputData<>("TEST2");

        ResultValidation result = validator.isValid(inputData);
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(ERROR_MSG+": TEST2",result.getError());
    }

}
