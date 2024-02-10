package com.kyc.core.util;

import org.springframework.ws.FaultAwareWebServiceMessage;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapEnvelope;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.client.SoapFaultClientException;

import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import  javax.xml.namespace.QName;

public final class SoapUtil {

    public static Date parseDate(String v) {
        return DatatypeConverter.parseDate(v).getTime();
    }

    public static String printDate(Date v)  {
        Calendar cal = new GregorianCalendar();
        cal.setTime(v);
        return DatatypeConverter.printDate(cal);
    }

    public static SoapMessage getSoapMessage(WebServiceMessage webServiceMessage){
        return (SoapMessage) webServiceMessage;
    }

    public static SoapBody getSoapBody(WebServiceMessage webServiceMessage){

        SoapMessage soapMessage = getSoapMessage(webServiceMessage);
        SoapEnvelope soapEnvelope = soapMessage.getEnvelope();
        return soapEnvelope.getBody();
    }

    public static SoapHeader getSoapHeader(WebServiceMessage webServiceMessage){

        SoapMessage soapMessage = getSoapMessage(webServiceMessage);
        SoapEnvelope soapEnvelope = soapMessage.getEnvelope();
        return soapEnvelope.getHeader();
    }

    public static boolean hasFault(final WebServiceMessage response) {
        if (response instanceof FaultAwareWebServiceMessage) {
            FaultAwareWebServiceMessage faultMessage = (FaultAwareWebServiceMessage) response;
            return faultMessage.hasFault();
        }
        return false;
    }

    public static void throwExceptionIfFault(final WebServiceMessage response){

        if(hasFault(response)){
            throw new SoapFaultClientException((SoapMessage) response);
        }
    }

    public static String getDetailContent(SoapFaultDetail soapFaultDetail, String typeDetail){

        Iterator<SoapFaultDetailElement> it = soapFaultDetail.getDetailEntries();
        String result = null;

        while(it.hasNext()){

            SoapFaultDetailElement element = it.next();



            QName qname = element.getName();
            if(qname.getLocalPart().equals(typeDetail)){
                result = qname.getLocalPart();;
                break;
            }
        }
        return result;
    }

    private SoapUtil(){}
}
