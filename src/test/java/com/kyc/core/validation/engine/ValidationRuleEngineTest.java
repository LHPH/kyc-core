package com.kyc.core.validation.engine;

import com.kyc.core.validation.model.InputData;
import com.kyc.core.validation.model.ResultValidation;
import com.kyc.core.validation.model.RuleValidation;
import com.kyc.core.validation.rules.ExpectPatternRuleValidation;
import com.kyc.core.validation.rules.NonNullRuleValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ValidationRuleEngineTest {

    private static final String ERROR_MSG = "ERROR";
    private static final String REGEX = "^TEST$";

    @Test
    public void evaluateRules_evaluateCorrectData_returnSuccessfulResults(){

        ValidationRuleEngine engine = new ValidationRuleEngine();

        InputData<String> inputData = new InputData<>("TEST");

        NonNullRuleValidation<String> rule1 = new NonNullRuleValidation<>(true,ERROR_MSG);
        ExpectPatternRuleValidation rule2 = new ExpectPatternRuleValidation(REGEX,true,ERROR_MSG);

        List<ResultValidation> results =  engine.evaluateRules(Arrays.asList(rule1,rule2),inputData);

        for(ResultValidation result : results){
            Assertions.assertTrue(result.isValid());
        }
    }

    @Test
    public void evaluateRulesGetFirstFailure_evaluateValidData_returnEmptyResult(){

        ValidationRuleEngine engine = new ValidationRuleEngine();

        InputData<String> inputData = new InputData<>("TEST");

        NonNullRuleValidation<String> rule1 = new NonNullRuleValidation<>(true,ERROR_MSG);
        ExpectPatternRuleValidation rule2 = new ExpectPatternRuleValidation(REGEX,true,ERROR_MSG);

        Optional<ResultValidation> result =  engine.evaluateRulesGetFirstFailure(Arrays.asList(rule1,rule2),inputData);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void evaluateRulesGetFirstFailure_evaluateInvalidData_returnUnsuccessfulResult(){

        ValidationRuleEngine engine = new ValidationRuleEngine();

        InputData<String> inputData = new InputData<>("TEST2");

        NonNullRuleValidation<String> rule1 = new NonNullRuleValidation<>(true,ERROR_MSG);
        ExpectPatternRuleValidation rule2 = new ExpectPatternRuleValidation(REGEX,true,ERROR_MSG);

        Optional<ResultValidation> result =  engine.evaluateRulesGetFirstFailure(Arrays.asList(rule1,rule2),inputData);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void evaluateRules_evaluateDifferentData_returnSuccessfulResult(){

        ValidationRuleEngine engine = new ValidationRuleEngine();

        InputData<Integer> inputData = new InputData<>(10);
        InputData<String> inputData2 = new InputData<>("TEST");

        RuleValidation<Integer> rule1 = new NonNullRuleValidation<>(true,ERROR_MSG);
        RuleValidation<String> rule2 = new ExpectPatternRuleValidation(REGEX,true,ERROR_MSG);

        List<InputData> inputDataList = new ArrayList<>();
        inputDataList.add(inputData);
        inputDataList.add(inputData2);

        List<RuleValidation> inputDataRules = new ArrayList<>();
        inputDataRules.add(rule1);
        inputDataRules.add(rule2);

        List<ResultValidation> results =  engine.evaluateRules(inputDataRules,inputDataList);

        for(ResultValidation result : results){
            Assertions.assertTrue(result.isValid());
        }
    }

    @Test
    public void getFirstFailure_evaluatingMixedResults_getFirstFailure(){

        List<ResultValidation> results = new ArrayList<>();
        results.add(ResultValidation.of(true));
        results.add(ResultValidation.of(false));
        results.add(ResultValidation.of(true));

        ValidationRuleEngine engine = new ValidationRuleEngine();

        Optional<ResultValidation> result = engine.getFirstFailure(results);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void getFirstFailure_evaluatingSuccessfulResults_getEmptyResult(){

        List<ResultValidation> results = new ArrayList<>();
        results.add(ResultValidation.of(true));
        results.add(ResultValidation.of(true));
        results.add(ResultValidation.of(true));

        ValidationRuleEngine engine = new ValidationRuleEngine();

        Optional<ResultValidation> result = engine.getFirstFailure(results);
        Assertions.assertFalse(result.isPresent());
    }


}
