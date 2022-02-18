package com.kyc.core.exception;

import com.kyc.core.model.MockObject;
import com.kyc.core.model.web.MessageData;
import com.kyc.core.model.web.MessageFieldErrorData;
import com.kyc.core.model.web.ResponseData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class KycValidationRestExceptionHandlerTest {

    private KycValidationRestExceptionHandler handler = new KycValidationRestExceptionHandler(new MessageData());

    @Test
    public void handleBadRequest_handleMethodArgumentNotValidException_returnResponseError() throws NoSuchMethodException {


        MethodParameter methodParameter = new MethodParameter(this.getClass().getMethod("method",String.class),0);
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(new MockObject(),"mockObject");
        result.rejectValue("stringField","error string value");

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter,result);

        ResponseEntity<ResponseData<Void>> response = handler.handleBadRequest(ex);

        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(response.getBody().getError() instanceof MessageFieldErrorData);
        Assert.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void handleBadRequest_handleMissingRequestHeaderException_returnResponseError() throws NoSuchMethodException {

        MethodParameter methodParameter = new MethodParameter(this.getClass().getMethod("method",String.class),0);
        MissingRequestHeaderException ex = new MissingRequestHeaderException("header_test",methodParameter);

        ResponseEntity<ResponseData<Void>> response = handler.handleBadRequest(ex);

        Assert.assertNotNull(response.getBody());
        Assert.assertNotNull(response.getBody().getError());
        Assert.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void handleBadRequest_handleMissingServletRequestParameterException_returnResponseError(){

        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("param","String");
        ResponseEntity<ResponseData<Void>> response = handler.handleBadRequest(ex);

        Assert.assertNotNull(response.getBody());
        Assert.assertNotNull(response.getBody().getError());
        Assert.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void handleBadRequest_handleMissingPathVariableException_returnResponseError() throws NoSuchMethodException {

        MethodParameter methodParameter = new MethodParameter(this.getClass().getMethod("method",String.class),0);
        MissingPathVariableException ex = new MissingPathVariableException("path_test",methodParameter);
        ResponseEntity<ResponseData<Void>> response = handler.handleBadRequest(ex);

        Assert.assertNotNull(response.getBody());
        Assert.assertNotNull(response.getBody().getError());
        Assert.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());

    }

    @Test
    public void handleBadRequest_handleHttpMessageNotReadableException_returnResponseError(){

        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("test",new MockHttpInputMessage(new byte[1]));
        ResponseEntity<ResponseData<Void>> response = handler.handleBadRequest(ex);

        Assert.assertNotNull(response.getBody());
        Assert.assertNotNull(response.getBody().getError());
        Assert.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void handleBadRequest_handleTypeMismatchException_returnResponseError(){

        TypeMismatchException ex = new TypeMismatchException("xy",Boolean.class);
        ResponseEntity<ResponseData<Void>> response = handler.handleBadRequest(ex);

        Assert.assertNotNull(response.getBody());
        Assert.assertNotNull(response.getBody().getError());
        Assert.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void handleBadRequest_handleUnsatisfiedServletRequestParameterException_returnResponseError(){

        String [] paramConditions = {"id"};
        Map<String,String []> actualParams = new HashMap<>();
        actualParams.put("test",new String[]{"value"});

        UnsatisfiedServletRequestParameterException ex = new UnsatisfiedServletRequestParameterException(paramConditions,actualParams);
        ResponseEntity<ResponseData<Void>> response = handler.handleBadRequest(ex);

        Assert.assertNotNull(response.getBody());
        Assert.assertNotNull(response.getBody().getError());
        Assert.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @SuppressWarnings("unused")
    public void method(String value){ }
}
