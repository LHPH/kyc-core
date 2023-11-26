package com.kyc.core.soap.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.ws.soap.SoapHeader;

import java.util.List;

@Getter
@Builder
public class RequestSoapData<T> {

    private List<SoapHeader> soapHeaders;
    private T payload;
}
