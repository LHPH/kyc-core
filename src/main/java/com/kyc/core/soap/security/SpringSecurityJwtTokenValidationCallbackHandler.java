package com.kyc.core.soap.security;

import com.kyc.core.security.SecureKycUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ott.OneTimeTokenAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ws.soap.security.callback.AbstractCallbackHandler;
import org.springframework.ws.soap.security.callback.CleanupCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

@RequiredArgsConstructor
public class SpringSecurityJwtTokenValidationCallbackHandler extends AbstractCallbackHandler {


    @Override
    protected void handleInternal(Callback callback) throws IOException, UnsupportedCallbackException {

        if (callback instanceof WSJwtCallback wsJwtCallback) {
            this.handleJwtToken(wsJwtCallback);
        }
        else if (callback instanceof CleanupCallback cleanupCallback) {
            this.handleCleanup(cleanupCallback);
        }
        else{
            throw new UnsupportedCallbackException(callback);
        }
    }

    protected void handleJwtToken(WSJwtCallback callback) {

        UserDetails userDetails = new SecureKycUser(callback.getJwtData());
        OneTimeTokenAuthentication authRequest = new OneTimeTokenAuthentication(
                callback.getJwtData(), userDetails.getAuthorities());
        authRequest.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authRequest);
    }

    protected void handleCleanup(CleanupCallback callback) throws IOException, UnsupportedCallbackException{
        SecurityContextHolder.clearContext();
    }
}
