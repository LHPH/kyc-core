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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("pathParams=").append(pathParams);
        sb.append(", queryParams=").append(queryParams);
        sb.append(", headers=").append(headers);
        sb.append(", body=").append(body);
        sb.append('}');
        return sb.toString();
    }
}
