package com.kyc.core.exception;

import com.kyc.core.enums.MessageType;
import com.kyc.core.model.web.MessageData;
import com.kyc.core.model.web.ResponseData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class KycUnhandledExceptionHandlerTest {

    private KycUnhandledExceptionHandler handler;

    @Test
    public void sendErrorResponse_processUnknownExceptionWithMessageData_returnErrorResponse(){

        handler = new KycUnhandledExceptionHandler(new MessageData("OTHER","MESS", MessageType.ERROR));
        ResponseEntity<ResponseData<Void>> response = handler.sendErrorResponse(new NullPointerException("null test"));

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals("OTHER",response.getBody().getError().getCode());
    }

    @Test
    public void sendErrorResponse_processUnknownExceptionWithoutMessageData_returnErrorResponse(){

        handler = new KycUnhandledExceptionHandler();
        ResponseEntity<ResponseData<Void>> response = handler.sendErrorResponse(new NullPointerException("null test"));

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals("ERROR",response.getBody().getError().getCode());
    }



}
