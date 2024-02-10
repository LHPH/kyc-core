package com.kyc.core.exception.handlers;

import com.kyc.core.enums.MessageType;
import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.MessageData;
import com.kyc.core.model.web.ResponseData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class KycGenericRestExceptionHandlerTest {

    private KycGenericRestExceptionHandler handler = new KycGenericRestExceptionHandler();

    @Test
    public void sendErrorResponse_processKycRestException_returnErrorResponse(){

        KycRestException kycRestException = KycRestException.builderRestException()
                .errorData(new MessageData("CODE","MESSAGE", MessageType.ERROR))
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        ResponseEntity<ResponseData<Void>> response = handler.sendErrorResponse(kycRestException);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals("CODE",response.getBody().getError().getCode());
    }
}
