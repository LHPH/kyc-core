package com.kyc.core.soap.interceptors;

import com.kyc.core.util.SoapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapFault;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SoapMessageInterceptor implements ClientInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapMessageInterceptor.class);

    private final boolean printMessage;

    public SoapMessageInterceptor(boolean printMessage){
        this.printMessage = printMessage;
    }

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {

        printWebServiceMessage(messageContext.getRequest(),"Request");
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {

        printWebServiceMessage(messageContext.getResponse(),"Response");
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {

        //SoapBody soapBody = SoapUtil.getSoapBody(messageContext.getResponse());
        //SoapFault soapFault = soapBody.getFault();
        LOGGER.info("**********************************");
        printWebServiceMessage(messageContext.getResponse(),"Fault");
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {}

    private void printWebServiceMessage(WebServiceMessage message, String type){

        //https://codenotfound.com/spring-ws-log-client-server-http-headers.html
        if(printMessage){

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try{
                message.writeTo(out);
                String content = out.toString(StandardCharsets.UTF_8.name());
                LOGGER.info("{}: {}",type,content);
            }
            catch(IOException ex){
                LOGGER.warn(type+" could not be printed",ex);
            }
        }
    }
}
