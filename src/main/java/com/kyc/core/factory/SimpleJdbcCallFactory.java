package com.kyc.core.factory;

import com.kyc.core.model.config.SimpleJdbcCallParams;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class SimpleJdbcCallFactory {

    private JdbcTemplate jdbcTemplate;

    public SimpleJdbcCallFactory(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public SimpleJdbcCall getSimpleJdbcCall(SimpleJdbcCallParams factoryParams, boolean isFunction){

        if(isFunction){
            return new SimpleJdbcCall(this.jdbcTemplate)
                    .withSchemaName(factoryParams.getSchemaName())
                    .withFunctionName(factoryParams.getFunctionName());
        }
        return new SimpleJdbcCall(this.jdbcTemplate)
                .withSchemaName(factoryParams.getSchemaName())
                .withProcedureName(factoryParams.getProcedureName());
    }
}
