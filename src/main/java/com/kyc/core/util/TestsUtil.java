package com.kyc.core.util;

import com.kyc.core.model.web.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class TestsUtil {

    public static <T> ResponseEntity<ResponseData<T>> getResponseTest(T body, HttpStatus httpStatus){

        ResponseData<T> responseData = ResponseData.of(body,httpStatus);
        return responseData.toResponseEntity();
    }

    public static <T> ResponseEntity<ResponseData<T>> getResponseTest(T body){
        return getResponseTest(body,HttpStatus.OK);
    }

    private TestsUtil(){}
}
