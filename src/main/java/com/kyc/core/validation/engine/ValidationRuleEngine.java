package com.kyc.core.validation.engine;

import com.kyc.core.validation.model.InputData;
import com.kyc.core.validation.model.ResultValidation;
import com.kyc.core.validation.model.RuleValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ValidationRuleEngine {


    public <T> List<ResultValidation> evaluateRules(List<RuleValidation<T>> rules, InputData<T> data){

        List<ResultValidation> results = new ArrayList<>();
        for(RuleValidation<T> rule : rules){
            ResultValidation result = rule.isValid(data);
            results.add(result);
        }
        return results;
    }

    public List<ResultValidation> evaluateRules(List<RuleValidation> rules, List<InputData> listData){

        List<ResultValidation> results = new ArrayList<>();
        int cont = 0;
        for(RuleValidation rule : rules){
            ResultValidation result = rule.isValid(listData.get(cont));
            results.add(result);
            cont++;
        }
        return results;
    }

    public <T> Optional<ResultValidation> evaluateRulesGetFirstFailure(List<RuleValidation<T>> rules, InputData<T> data){

        for(RuleValidation<T> rule : rules){

            ResultValidation result = rule.isValid(data);
            if(!result.isValid()){
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }



    public Optional<ResultValidation> getFirstFailure(List<ResultValidation> results){

        return results.stream()
                .filter(result -> !result.isValid())
                .findFirst();
    }
}
