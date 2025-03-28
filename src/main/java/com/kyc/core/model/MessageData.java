package com.kyc.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kyc.core.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageData extends BaseModel {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("type")
    private MessageType type;

    @JsonProperty("time")
    private Instant time = Instant.now();

    @JsonIgnore
    private String hint;

    public MessageData(String code, String message, MessageType type) {

        this.code = code;
        this.message = message;
        this.type = type;
    }

    public MessageData(MessageData messageData){

        this.code = messageData.getCode();
        this.message = messageData.getMessage();
        this.type = messageData.getType();
        this.time = messageData.getTime();
        this.hint = messageData.getHint();
    }

    public static MessageData copy(MessageData original){

        MessageData messageData = new MessageData();
        messageData.setCode(original.code);
        messageData.setMessage(original.message);
        messageData.setType(original.type);
        messageData.setHint(original.hint);

        return messageData;
    }
}
