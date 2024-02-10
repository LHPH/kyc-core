package com.kyc.core.exception.handlers;

import com.kyc.core.exception.KycSoapException;
import com.kyc.core.model.MessageData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.AbstractSoapFaultDefinitionExceptionResolver;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;

import javax.xml.transform.Result;
import java.io.IOException;

public class KycGenericSoapExceptionHandler extends AbstractSoapFaultDefinitionExceptionResolver {

    private static final Logger LOGGER = LogManager.getLogger(KycGenericSoapExceptionHandler.class);

    private final MessageData messageData;
    private final Marshaller marshaller;

    public KycGenericSoapExceptionHandler(MessageData messageData) {

        this.messageData = messageData;
        marshaller = new Jaxb2Marshaller();
        ((Jaxb2Marshaller)marshaller).setClassesToBeBound(MessageData.class);
    }

    public KycGenericSoapExceptionHandler(MessageData messageData, Marshaller marshaller) {
        this.messageData = messageData;
        this.marshaller = marshaller;
    }

    @Override
    protected SoapFaultDefinition getFaultDefinition(Object o, Exception e) {

        SoapFaultDefinition definition = new SoapFaultDefinition();
        LOGGER.error(" ",e);
        if(e instanceof KycSoapException){

            KycSoapException kycSoapException = (KycSoapException)e;
            definition.setFaultCode(kycSoapException.getFaultCode());
            definition.setFaultStringOrReason(kycSoapException.getErrorData().getCode());
        }
        else{
            definition.setFaultCode(SoapFaultDefinition.SERVER);
            definition.setFaultStringOrReason(this.messageData.getCode());
        }

        return definition;
    }

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {

        try{
            MessageData messageData;
            if(ex instanceof KycSoapException){

                KycSoapException kycSoapException = (KycSoapException)ex;
                messageData = kycSoapException.getErrorData();
            }
            else{
                messageData = this.messageData;
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
