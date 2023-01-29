package com.kyc.core.validation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultValidation {

    private boolean valid;
    private String code;
    private String field;
    private String error;

    public static ResultValidation of(boolean result){
        return ResultValidation.builder()
                .valid(result)
                .build();
    }
}
