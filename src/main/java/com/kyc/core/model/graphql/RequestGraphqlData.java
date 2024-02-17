package com.kyc.core.model.graphql;

import com.kyc.core.model.BaseModel;
import graphql.schema.DataFetchingEnvironment;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder(toBuilder = true)
public class RequestGraphqlData<T> extends BaseModel {

    private Map<String,Object> arguments;
    private T payload;
    private DataFetchingEnvironment environment;
}
