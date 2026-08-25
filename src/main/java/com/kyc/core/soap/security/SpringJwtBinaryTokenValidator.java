package com.kyc.core.soap.security;


import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.jwt.JwtData;
import com.kyc.core.security.jwt.KycUserTokenSessionService;
import com.kyc.core.util.TokenUtil;
import lombok.AllArgsConstructor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.Jwt;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class SpringJwtBinaryTokenValidator implements Validator {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpringJwtBinaryTokenValidator.class);

    private final KycUserTokenSessionService kycUserTokenSessionService;

    @Override
    public Credential validate(Credential credential, RequestData requestData) throws WSSecurityException {

        try {

            BinarySecurity binarySecurity = credential.getBinarySecurityToken();

            if(binarySecurity == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
            }

            String token = new String(binarySecurity.getToken(), StandardCharsets.UTF_8);

            Jwt jwt = kycUserTokenSessionService.retrieveInfoUserToken(token);
            JwtData jwtData = TokenUtil.transform(jwt);

            WSJwtCallback wsJwtCallback = new WSJwtCallback(jwtData);
            requestData.getCallbackHandler().handle(new Callback[]{wsJwtCallback});

            return credential;
        } catch (IOException | UnsupportedCallbackException | KycRestException ex) {
            LOGGER.error(" ",ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, ex);
        }
    }
}
