package com.kyc.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kyc.core.enums.MessageType;
import io.github.threetenjaxb.core.InstantXmlAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Instant;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "code",
        "message",
        "type",
        "time"
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlRootElement(name = "error")
public class MessageData extends BaseModel {

    @JsonProperty("code")
    @XmlElement(name = "code", required = true)
    private String code;

    @JsonProperty("message")
    @XmlElement(name = "message", required = true)
    private String message;

    @JsonProperty("type")
    @XmlElement(name = "type", required = true)
    private MessageType type;

    @JsonProperty("time")
    @XmlElement(name = "time", required = true)
    @XmlJavaTypeAdapter(InstantXmlAdapter.class)
    @XmlSchemaType(name = "dateTime")
    private final Instant time = Instant.now();

    public static MessageData copy(MessageData original){

        MessageData messageData = new MessageData();
        messageData.setCode(original.code);
        messageData.setMessage(original.message);
        messageData.setType(original.type);

        return messageData;
    }
}
