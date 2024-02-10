package com.kyc.core.exception.handlers;

import com.kyc.core.model.MockObject;
import com.kyc.core.model.MessageData;
import com.kyc.core.model.web.MessageFieldErrorData;
import com.kyc.core.model.web.ResponseData;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

@ExtendWith(MockitoExtension.class)
public class KycValidationReactiveRestExceptionHandlerTest {

    private KycValidationReactiveRestExceptionHandler handler = new KycValidationReactiveRestExceptionHandler(new MessageData());

    @Test
    public void handleBadRequest_handleBadRequestWebExchangeBindException_returnError() throws NoSuchMethodException {

        MethodParameter methodParameter = new MethodParameter(this.getClass().getMethod("method",String.class),0);
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(new MockObject(),"mockObject");
        result.rejectValue("stringField","error string value");

        WebExchangeBindException ex = new WebExchangeBindException(methodParameter,result);

        ResponseEntity<ResponseData<Void>> response =  handler.handleBadRequest(ex);

        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(response.getBody().getError() instanceof MessageFieldErrorData);
        Assert.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void handleBadRequest_handleBadRequestServerWebInputException_returnError(){

        ServerWebInputException ex = new ServerWebInputException("bad param");
        ResponseEntity<ResponseData<Void>> response =  handler.handleBadRequest(ex);

        Assert.assertNotNull(response.getBody());
        Assert.assertNotNull(response.getBody().getError());
        Assert.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @SuppressWarnings("unused")
    public void method(String value){ }
}
