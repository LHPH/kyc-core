package com.kyc.core.exception.handlers;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.MessageData;
import com.kyc.core.properties.KycMessages;
import com.kyc.core.util.IntegrationUtil;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;

import static com.kyc.core.enums.MessageHint.UNEXPECTED;

@RequiredArgsConstructor
public class KycMessagingExceptionHandler implements GenericHandler<ErrorMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KycMessagingExceptionHandler.class);

    private final KycMessages kycMessages;

    @Override
    public @Nullable Object handle(ErrorMessage errorMessage, MessageHeaders headers) {

        Throwable cause = errorMessage.getPayload().getCause();
        if(cause == null){
            cause= errorMessage.getPayload();
        }

        LOGGER.error(" ",cause);
        MessageData messageData;
        if(cause instanceof KycException kycException){
            messageData = kycException.getErrorData();
        }
        else{
            messageData = kycMessages.getMessageByHint(UNEXPECTED);
        }

        return IntegrationUtil.getTcpResponseFromMessage(messageData);
    }
}
