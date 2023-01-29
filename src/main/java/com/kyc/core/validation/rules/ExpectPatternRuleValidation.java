package com.kyc.core.validation.rules;

import com.kyc.core.validation.model.InputData;
import com.kyc.core.validation.model.ResultValidation;

import java.util.regex.Pattern;

public class ExpectPatternRuleValidation extends AbstractRuleValidation<String> {

    private final Pattern pattern;

    public ExpectPatternRuleValidation(String regex, boolean required, String message){
        super(required,message);
        pattern = Pattern.compile(regex);
    }

    @Override
    public ResultValidation validate(InputData<String> inputData) {

        String value = inputData.getData();
        if(value!=null){

            if(pattern.matcher(value).matches()){

                return ResultValidation.builder()
                        .valid(true)
                        .build();
            }
        }
        return ResultValidation.builder()
                .valid(false)
                .field(inputData.getFieldName())
                .error(getErrorMessage()+": "+value)
                .build();

    }
}
