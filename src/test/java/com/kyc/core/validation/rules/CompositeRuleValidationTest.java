package com.kyc.core.validation.rules;

import com.kyc.core.validation.model.InputData;
import com.kyc.core.validation.model.ResultValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class CompositeRuleValidationTest {

    private static final String ERROR_MSG = "ERROR";
    private static final String REGEX = "^TEST$";

    @Test
    public void isValid_validateRequiredWithNotNullAndExpectedValue_returnSuccessfulResult(){

        NonNullRuleValidation<String> validator = new NonNullRuleValidation<>(true,ERROR_MSG);
        ExpectPatternRuleValidation validator2 = new ExpectPatternRuleValidation(REGEX,true,ERROR_MSG);

        CompositeRuleValidation<String> compositeRule = new CompositeRuleValidation<>(true);

        compositeRule.setDelegates(Arrays.asList(validator,validator2));

        InputData<String> inputData = new InputData<>("TEST");

        ResultValidation result = compositeRule.isValid(inputData);
        Assertions.assertTrue(result.isValid());
        Assertions.assertNull(result.getError());
    }

    @Test
    public void isValid_validateNoRequiredWithNotNullAndExpectedValue_returnSuccessfulResult(){

        NonNullRuleValidation<String> validator = new NonNullRuleValidation<>(true,ERROR_MSG);
        ExpectPatternRuleValidation validator2 = new ExpectPatternRuleValidation(REGEX,true,ERROR_MSG);

        CompositeRuleValidation<String> compositeRule = new CompositeRuleValidation<>(false);

        compositeRule.setDelegates(Arrays.asList(validator,validator2));

        InputData<String> inputData = new InputData<>("TEST");

        ResultValidation result = compositeRule.isValid(inputData);
        Assertions.assertTrue(result.isValid());
        Assertions.assertNull(result.getError());
    }

    @Test
    public void isValid_validateRequiredWithBadValueForFirstRule_returnUnsuccessfulResult(){

        NonNullRuleValidation<String> validator = new NonNullRuleValidation<>(true,ERROR_MSG);
        ExpectPatternRuleValidation validator2 = new ExpectPatternRuleValidation(REGEX,true,ERROR_MSG);

        CompositeRuleValidation<String> compositeRule = new CompositeRuleValidation<>(true);

        compositeRule.setDelegates(Arrays.asList(validator,validator2));

        InputData<String> inputData = new InputData<>(null);

        ResultValidation result = compositeRule.isValid(inputData);
        Assertions.assertFalse(result.isValid());
        Assertions.assertNotNull(result.getError());
    }

    @Test
    public void isValid_validateRequiredWithBadValueForSecondRule_returnUnsuccessfulResult(){

        NonNullRuleValidation<String> validator = new NonNullRuleValidation<>(true,ERROR_MSG);
        ExpectPatternRuleValidation validator2 = new ExpectPatternRuleValidation(REGEX,true,ERROR_MSG);

        CompositeRuleValidation<String> compositeRule = new CompositeRuleValidation<>(true);

        compositeRule.setDelegates(Arrays.asList(validator,validator2));

        InputData<String> inputData = new InputData<>("TEST2");

        ResultValidation result = compositeRule.isValid(inputData);
        Assertions.assertFalse(result.isValid());
        Assertions.assertNotNull(result.getError());
    }
}
