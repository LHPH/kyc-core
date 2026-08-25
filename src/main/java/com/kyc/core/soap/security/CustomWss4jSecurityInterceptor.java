package com.kyc.core.soap.security;

import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityValidationException;

import java.util.List;

import static org.apache.wss4j.dom.engine.WSSecurityEngineResult.TAG_ACTION;

public class CustomWss4jSecurityInterceptor extends Wss4jSecurityInterceptor {

    @Override
    protected void checkResults(List<WSSecurityEngineResult> results, List<Integer> validationActions) throws Wss4jSecurityValidationException {

        results.stream()
                .map(result -> (Integer) result.get(TAG_ACTION))
                .filter(action -> action == WSConstants.UT || action == WSConstants.BST)
                .findFirst()
                .orElseThrow(() -> new Wss4jSecurityValidationException("Security processing failed, only accept UsernameToken or BinaryToken"));
    }
}
