package com.kyc.core.enums;

import org.apache.commons.lang3.ObjectUtils;

public enum MessageType {

    INFO,WARN,ERROR;

    public static MessageType resolve(String message, MessageType defaultCase){

        MessageType result = null;
        for(MessageType type : MessageType.values()){
            if(type.toString().equalsIgnoreCase(message)){
                result = type;
                break;
            }
        }
        return ObjectUtils.defaultIfNull(result,defaultCase);
    }
}
