package com.kyc.core.model.web;


import com.kyc.core.model.BaseModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldErrorData extends BaseModel {

    private String field;
    private String value;
    private String message;
}
