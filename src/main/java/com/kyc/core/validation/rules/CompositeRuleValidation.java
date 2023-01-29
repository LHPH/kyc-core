package com.kyc.core.validation.rules;

import com.kyc.core.validation.model.InputData;
import com.kyc.core.validation.model.ResultValidation;
import com.kyc.core.validation.model.RuleValidation;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class CompositeRuleValidation<T> extends AbstractRuleValidation<T>{

    private List<RuleValidation<T>> delegates = new ArrayList<>();

    public CompositeRuleValidation(boolean required){
        super(required);
    }

    @Override
    public ResultValidation validate(InputData<T> inputData) {

        for(RuleValidation<T> rule : delegates){

            ResultValidation result = rule.isValid(inputData);
            if(!result.isValid()){
                return result;
            }
        }
        return ResultValidation.builder()
                .valid(true)
                .build();
    }
}
