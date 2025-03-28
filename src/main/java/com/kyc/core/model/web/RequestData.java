package com.kyc.core.model.web;


import com.kyc.core.model.BaseModel;
import com.kyc.core.model.jwt.JwtData;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder(toBuilder = true)
@Getter
public class RequestData<T> extends BaseModel {

    private Map<String,Object> pathParams;
    private Map<String,String> queryParams;
    private Map<String,Object> headers;
    private T body;
    private JwtData auth;
}
