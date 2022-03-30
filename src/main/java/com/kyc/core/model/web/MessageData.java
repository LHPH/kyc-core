package com.kyc.core.model.web;

import com.kyc.core.enums.MessageType;
import com.kyc.core.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageData extends BaseModel {

    private String code;
    private String message;
    private MessageType type;
    private final Instant time = Instant.now();

    public static MessageData copy(MessageData original){

        MessageData messageData = new MessageData();
        messageData.setCode(original.code);
        messageData.setMessage(original.message);
        messageData.setType(original.type);

        return messageData;
    }
}
