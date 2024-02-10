package com.kyc.core.soap;

import com.kyc.core.exception.KycSoapException;
import com.kyc.core.model.MessageData;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.FaultMessageResolver;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.transform.Source;
import java.io.IOException;

public class MessageDataSoapFaultResolver implements FaultMessageResolver {

    private final Unmarshaller unmarshaller;

    public MessageDataSoapFaultResolver() {
        unmarshaller = new Jaxb2Marshaller();
        ((Jaxb2Marshaller)unmarshaller).setClassesToBeBound(MessageData.class);
    }

    @Override
    public void resolveFault(WebServiceMessage message) throws IOException {

        SoapMessage soapMessage = (SoapMessage) message;
        SoapFault soapFault = soapMessage.getSoapBody().getFault();

        Source source = soapFault.getFaultDetail().getDetailEntries().next().getSource();
        MessageData messageData = (MessageData) unmarshaller.unmarshal(source);

        throw KycSoapException.builderSoapException()
                .faultCode(soapFault.getFaultCode())
                .outputData(message)
                .errorData(messageData)
                .build();
    }
}
