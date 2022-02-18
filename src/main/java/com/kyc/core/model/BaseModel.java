package com.kyc.core.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class BaseModel implements Serializable {

    public String toString(){

        return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
    }
}
