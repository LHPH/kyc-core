package com.kyc.core.model.web;


import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class RequestData<T> {

    private Map<String,Object> pathParams;
    private Map<String,String> queryParams;
    private Map<String,Object> headers;
    private T body;
}
