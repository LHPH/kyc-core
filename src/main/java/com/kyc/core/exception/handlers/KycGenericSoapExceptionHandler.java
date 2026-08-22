package com.kyc.core.exception.handlers;

import com.kyc.core.exception.KycSoapException;
import com.kyc.core.model.XmlMessageData;
import com.kyc.core.properties.KycMessages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityValidationException;
import org.springframework.ws.soap.server.endpoint.AbstractSoapFaultDefinitionExceptionResolver;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;

import javax.xml.transform.Result;
import java.io.IOException;

public class KycGenericSoapExceptionHandler extends AbstractSoapFaultDefinitionExceptionResolver {

    private static final Logger LOGGER = LogManager.getLogger(KycGenericSoapExceptionHandler.class);

    private final KycMessages kycMessages;
    private final Marshaller marshaller;

    public KycGenericSoapExceptionHandler(KycMessages kycMessages) {

        this.kycMessages = kycMessages;
        this.marshaller = new Jaxb2Marshaller();
        ((Jaxb2Marshaller)marshaller).setClassesToBeBound(XmlMessageData.class);
    }

    public KycGenericSoapExceptionHandler(KycMessages kycMessages, Marshaller marshaller) {
        this.kycMessages = kycMessages;
        this.marshaller = marshaller;
    }

    @Override
    protected SoapFaultDefinition getFaultDefinition(Object o, Exception e) {

        SoapFaultDefinition definition = new SoapFaultDefinition();
        LOGGER.error(" ",e);
        if(e instanceof KycSoapException kycSoapException){

            definition.setFaultCode(kycSoapException.getFaultCode());
            definition.setFaultStringOrReason(kycSoapException.getErrorData().getCode());
        }
        else if(e instanceof Wss4jSecurityValidationException){

            XmlMessageData messageData = new XmlMessageData(kycMessages.getMessageByHint("AUTH"));
            definition.setFaultCode(SoapFaultDefinition.CLIENT);
            definition.setFaultStringOrReason(messageData.getCode());

        }
        else{

            XmlMessageData messageData = new XmlMessageData(kycMessages.getMessageByHint("UNEXPECTED"));
            definition.setFaultCode(SoapFaultDefinition.SERVER);
            definition.setFaultStringOrReason(messageData.getCode());
        }

        return definition;
    }

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {

        try{
            XmlMessageData messageData;
            if(ex instanceof KycSoapException kycSoapException){

                messageData = (XmlMessageData) kycSoapException.getErrorData();
            }
            else if(ex instanceof Wss4jSecurityValidationException){

                messageData = new XmlMessageData(kycMessages.getMessageByHint("AUTH"));
            }
            else{

                messageData = new XmlMessageData(kycMessages.getMessageByHint("UNEXPECTED"));
            }

            SoapFaultDetail soapFaultDetail = fault.addFaultDetail();
            Result result = soapFaultDetail.getResult();
            marshaller.marshal(messageData,result);
        }
        catch (IOException jaxbex){
            LOGGER.error("No se pudo hacer la conversion ",jaxbex);
        }
    }
}
