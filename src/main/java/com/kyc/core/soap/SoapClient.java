package com.kyc.core.soap;

import com.kyc.core.enums.MessageType;
import com.kyc.core.exception.KycSoapException;
import com.kyc.core.model.MessageData;
import com.kyc.core.soap.model.RequestSoapData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.support.MarshallingUtils;

import javax.xml.transform.Result;
import javax.xml.transform.TransformerException;

import java.io.IOException;

import static com.kyc.core.util.SoapUtil.getDetailContent;
import static com.kyc.core.util.SoapUtil.throwExceptionIfFault;

public class SoapClient<I,O> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapClient.class);
    private final String url;
    private final WebServiceTemplate template;
    private WebServiceMessageCallback requestCallback;
    private WebServiceMessageExtractor messageExtractor;

    public SoapClient(String url, WebServiceTemplate template){
        this.template = template;
        this.url = url;
    }

    @SuppressWarnings("unchecked")
    public O executeService(RequestSoapData<I> requestData){

        LOGGER.info("Calling SOAP WS");
        return (O) template.sendAndReceive(url, new WebServiceMessageCallback() {
                    @Override
                    public void doWithMessage(WebServiceMessage webServiceMessage) throws IOException, TransformerException {

                        MarshallingUtils.marshal(template.getMarshaller(), requestData.getPayload(), webServiceMessage);
                        if (requestCallback != null) {
                            requestCallback.doWithMessage(webServiceMessage);
                        }
                    }
                },
                new WebServiceMessageExtractor<Object>() {
                    @Override
                    public Object extractData(WebServiceMessage webServiceMessage) throws IOException, TransformerException {

                        if(messageExtractor!=null){
                            return messageExtractor.extractData(webServiceMessage);
                        }
                        else{
                            throwExceptionIfFault(webServiceMessage);
                            return MarshallingUtils.unmarshal(template.getUnmarshaller(), webServiceMessage);
                        }
                    }
                });
       /* try{

        }
        catch(SoapFaultClientException ex){

            SoapFault soapFault = ex.getSoapFault();

            Result result = soapFault.getFaultDetail().getResult();

            MessageData messageData = (MessageData) unmarshaller.unmarshal(result);

            throw KycSoapException.builderSoapException()
                    .exception(ex)
                    .faultCode(soapFault.getFaultCode())
                    .inputData(requestData)
                    .errorData(messageData)
                    .outputData(ex.getSoapFault())
                    .build();
        }
        catch(WebServiceClientException ex){

            LOGGER.error(" ",ex);
            throw KycSoapException.builderSoapException()
                    .exception(ex)
                    .inputData(requestData)
                    .build();
        }*/
    }
}
