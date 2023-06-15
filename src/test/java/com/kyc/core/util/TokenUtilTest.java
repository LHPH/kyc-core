package com.kyc.core.util;

import com.kyc.core.model.jwt.JWTData;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.text.ParseException;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class TokenUtilTest {


    @Test
    public void generateRandomSharedSecret_generateRandomSharedSecret_returnRandomSharedSecret(){

        byte[] sharedSecret = TokenUtil.generateRandomSharedSecret(32);
        Assertions.assertNotNull(sharedSecret);
    }

    @Test
    public void getToken_generateToken_returnToken() throws JOSEException {

        JWTData jwtData = new JWTData();
        jwtData.setKey("key");
        jwtData.setAudience("audience");
        jwtData.setChannel("channel");
        jwtData.setExpirationTime(DateUtil.localDateToDate(LocalDate.now().plusDays(1)));

        byte[] sharedSecret = TokenUtil.generateRandomSharedSecret(32);

        String token = TokenUtil.getToken(jwtData,JWSAlgorithm.HS256,sharedSecret);
        Assertions.assertNotNull(token);
    }

    @Test
    public void getJwtData_getJwtDataFromToken_returnJwtData() throws JOSEException, ParseException {

        JWTData jwtData = new JWTData();
        jwtData.setKey("key");
        jwtData.setAudience("audience");
        jwtData.setChannel("channel");
        jwtData.setExpirationTime(DateUtil.localDateToDate(LocalDate.now().plusDays(1)));

        byte[] sharedSecret = TokenUtil.generateRandomSharedSecret(32);

        String token = TokenUtil.getToken(jwtData,JWSAlgorithm.HS256,sharedSecret);

        JWTData result = TokenUtil.getJwtData(token,JWSAlgorithm.HS256,sharedSecret);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(jwtData.getKey(),result.getKey());
        Assertions.assertEquals(jwtData.getAudience(),result.getAudience());
        Assertions.assertEquals(jwtData.getChannel(),result.getChannel());
    }

    @Test
    public void checkExpirationTime_checkingGoodTime_returnTrue(){

        JWTData jwtData = new JWTData();
        jwtData.setExpirationTime(DateUtil.localDateToDate(LocalDate.now().plusDays(1)));

        Assertions.assertTrue(TokenUtil.checkExpirationTime(jwtData));
    }

    @Test
    public void checkExpirationTime_checkingBadTime_returnFalse(){

        JWTData jwtData = new JWTData();
        jwtData.setExpirationTime(DateUtil.localDateToDate(LocalDate.now().minusDays(1)));

        Assertions.assertFalse(TokenUtil.checkExpirationTime(jwtData));
    }

    @Test
    public void checkExpirationTime_noExpirationTime_returnFalse(){

        JWTData jwtData = new JWTData();
        jwtData.setExpirationTime(null);

        Assertions.assertFalse(TokenUtil.checkExpirationTime(jwtData));
    }

    @Test
    public void extractTokenFromAuthHeader_receiveWellFormedBearerToken_returnTokenPart(){

        String bearerToken = "Bearer Token";
        Assertions.assertEquals("Token",TokenUtil.extractTokenFromAuthHeader(bearerToken));
    }

    @Test
    public void extractTokenFromAuthHeader_receiveBadBearerToken_raiseException(){

        Assertions.assertThrows(OAuth2AuthenticationException.class,()->{
            String bearerToken = "Token";
            TokenUtil.extractTokenFromAuthHeader(bearerToken);
        });
    }

    @Test
    public void extractTokenFromAuthHeader_receiveNullToken_raiseException(){

        Assertions.assertThrows(OAuth2AuthenticationException.class,()->{
            String bearerToken = null;
            TokenUtil.extractTokenFromAuthHeader(bearerToken);
        });
    }
}
