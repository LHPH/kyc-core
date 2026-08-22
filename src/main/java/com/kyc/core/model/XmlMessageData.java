package com.kyc.core.model;

import com.kyc.core.enums.MessageType;
import io.github.threetenjaxb.core.InstantXmlAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "code",
        "message",
        "type",
        "time",
        "hint"
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlRootElement(name = "error")
public class XmlMessageData extends MessageData{

    @XmlElement(name = "code", required = true)
    private String code;

    @XmlElement(name = "message", required = true)
    private String message;

    @XmlElement(name = "type", required = true)
    private MessageType type;

    @XmlElement(name = "time", required = true)
    @XmlJavaTypeAdapter(InstantXmlAdapter.class)
    @XmlSchemaType(name = "dateTime")
    private Instant time = Instant.now();

    @XmlElement(name = "hint",nillable = true)
    private String hint;

    public XmlMessageData(String code, String message, MessageType type) {

        super(code,message,type);
        this.code = code;
        this.message = message;
        this.type = type;
    }

    public XmlMessageData(MessageData messageData){

        super(messageData);
        this.code = messageData.getCode();
        this.message = messageData.getMessage();
        this.type = messageData.getType();
        this.time = messageData.getTime();
        this.hint = messageData.getHint();
    }

    public static XmlMessageData copy(XmlMessageData original){

        XmlMessageData messageData = new XmlMessageData();
        messageData.setCode(original.code);
        messageData.setMessage(original.message);
        messageData.setType(original.type);
        messageData.setHint(original.hint);

        return messageData;
    }
}
