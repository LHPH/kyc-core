package com.kyc.core.soap.security;

import lombok.RequiredArgsConstructor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class CompositeSoapSecurityValidator implements Validator {

    private final static Logger LOGGER = LoggerFactory.getLogger(CompositeSoapSecurityValidator.class);

    private final SpringJwtBinaryTokenValidator springJwtBinaryTokenValidator;
    private final SpringUsernameTokenValidator springUsernameTokenValidator;

    @Override
    public Credential validate(Credential credential, RequestData requestData) throws WSSecurityException {

        if(credential.getBinarySecurityToken()==null){
            return springUsernameTokenValidator.validate(credential,requestData);
        }

        if(credential.getUsernametoken()==null){
            return springJwtBinaryTokenValidator.validate(credential,requestData);
        }

        LOGGER.error("Unsupported credential type");
        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
    }
}
