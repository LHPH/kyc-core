package com.kyc.core.soap;

import com.kyc.core.enums.MessageType;
import com.kyc.core.exception.KycSoapException;
import com.kyc.core.model.web.MessageData;
import com.kyc.core.soap.model.RequestSoapData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.client.SoapFaultClientException;

import static com.kyc.core.util.SoapUtil.getDetailContent;

public class SoapClient<I,O> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapClient.class);

    private final String url;
    private final WebServiceTemplate template;

    public SoapClient(String url, WebServiceTemplate template){
        this.template = template;
        this.url = url;
    }

    @SuppressWarnings("unchecked")
    public O executeService(RequestSoapData<I> requestData){

        try{
            LOGGER.info("Calling SOAP WS");
            return (O) template.marshalSendAndReceive(url,requestData.getPayload());
        }
        catch(SoapFaultClientException ex){

            SoapFault soapFault = ex.getSoapFault();

            String code = soapFault.getFaultStringOrReason();
            String message = getDetailContent(soapFault.getFaultDetail(), "message");
            MessageType type = MessageType.resolve(getDetailContent(soapFault.getFaultDetail(),"type"),MessageType.ERROR);

            throw KycSoapException.builderSoapException()
                    .exception(ex)
                    .faultCode(soapFault.getFaultCode())
                    .inputData(requestData)
                    .errorData(new MessageData(code,message,type))
                    .outputData(ex.getSoapFault())
                    .build();
        }
        catch(WebServiceClientException ex){

            LOGGER.error(" ",ex);
            throw KycSoapException.builderSoapException()
                    .exception(ex)
                    .inputData(requestData)
                    .build();
        }
    }
}
