package com.kyc.core.util;

import com.kyc.core.model.MessageData;

import static com.kyc.core.constants.IntegrationConstants.RESPONSE_FORMAT;

public final class IntegrationUtil {

    public static String getTcpResponseFromMessage(MessageData messageData){

        return String.format(
                RESPONSE_FORMAT,
                messageData.getCode(),
                messageData.getMessage(),
                messageData.getType().name()
        );
    }

    private IntegrationUtil(){}
}
