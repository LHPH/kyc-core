package com.kyc.core.soap.security;

import lombok.AllArgsConstructor;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.UsernameToken;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

@AllArgsConstructor
public class SpringUsernameTokenValidator implements Validator {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpringUsernameTokenValidator.class);

    private PasswordEncoder passwordEncoder;

    @Override
    public Credential validate(Credential credential, RequestData requestData) throws WSSecurityException {

        try {

            UsernameToken usernameToken = credential.getUsernametoken();
            String user = usernameToken.getName();
            String secret = usernameToken.getPassword();
            String pwType = usernameToken.getPasswordType();

            WSPasswordCallback pwCb = new WSPasswordCallback(user, null, pwType, WSPasswordCallback.USERNAME_TOKEN);
            requestData.getCallbackHandler().handle(new Callback[]{pwCb});

            String hash = pwCb.getPassword();
            if (hash == null || !passwordEncoder.matches(secret,hash)) {
                LOGGER.warn("BAD CREDENTIALS");
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
            }

        } catch (UnsupportedCallbackException | IOException ex) {
            LOGGER.error(" ",ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, ex);
        }

        return credential;
    }
}
