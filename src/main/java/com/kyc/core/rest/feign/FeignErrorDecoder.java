package com.kyc.core.rest.feign;

import feign.Response;
import feign.codec.ErrorDecoder;


public class FeignErrorDecoder implements ErrorDecoder {

    ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {

        return errorDecoder.decode(s,response);
    }
}
