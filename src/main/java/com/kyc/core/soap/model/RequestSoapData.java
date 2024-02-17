package com.kyc.core.soap.model;

import com.kyc.core.model.BaseModel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.ws.soap.SoapHeader;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class RequestSoapData<T> extends BaseModel {

    private List<SoapHeader> soapHeaders;
    private T payload;
}
