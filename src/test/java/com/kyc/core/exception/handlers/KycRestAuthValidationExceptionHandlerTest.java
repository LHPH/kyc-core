package com.kyc.core.exception.handlers;

import com.kyc.core.model.MessageData;
import com.kyc.core.model.web.ResponseData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

@ExtendWith(MockitoExtension.class)
public class KycRestAuthValidationExceptionHandlerTest {

    private KycRestAuthValidationExceptionHandler handler = new KycRestAuthValidationExceptionHandler(new MessageData());

    @Test
    public void handleOAuth2AuthenticationException_processException_returnUnauthorizedResponse(){

        OAuth2AuthenticationException ex = new OAuth2AuthenticationException("bad auth");

        ResponseEntity<ResponseData<Void>> response = handler.handleOAuth2AuthenticationException(ex);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
    }
}
