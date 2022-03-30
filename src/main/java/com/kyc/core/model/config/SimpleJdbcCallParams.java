package com.kyc.core.model.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleJdbcCallParams {

    private final String procedureName;
    private final String functionName;
    private final String schemaName;
}
