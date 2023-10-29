package com.kyc.core.exception.handlers;

import com.kyc.core.exception.KycSoapException;
import com.kyc.core.model.web.MessageData;
import com.kyc.core.util.DateUtil;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.AbstractSoapFaultDefinitionExceptionResolver;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;

import javax.xml.namespace.QName;
import java.util.Date;

@AllArgsConstructor
public class KycGenericSoapExceptionHandler extends AbstractSoapFaultDefinitionExceptionResolver {

    private static final Logger LOGGER = LogManager.getLogger(KycGenericSoapExceptionHandler.class);

    private MessageData messageData;

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

        String message;
        String type;
        String timestamp;
        if(ex instanceof KycSoapException){

            KycSoapException kycSoapException = (KycSoapException)ex;
            MessageData messageData = kycSoapException.getErrorData();

            message = messageData.getMessage();
            type = messageData.getType().toString();
            timestamp = DateUtil.dateToString(Date.from(messageData.getTime()),"yyyy-MM-dd HH:mm:ss");
        }
        else{

            message = this.messageData.getMessage();
            type = this.messageData.getType().toString();
            timestamp = DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss");
        }

        SoapFaultDetail soapFaultDetail = fault.addFaultDetail();
        soapFaultDetail.addFaultDetailElement(QName.valueOf("message"))
                .addText(message);
        soapFaultDetail.addFaultDetailElement(QName.valueOf("type"))
                .addText(type);
        soapFaultDetail.addFaultDetailElement(QName.valueOf("time"))
                .addText(timestamp);
    }
}
