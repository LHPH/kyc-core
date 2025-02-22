package com.kyc.core.security.jwt;

import com.kyc.core.exception.KycRestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

@RequiredArgsConstructor
public class KycUserSessionTokenJwtDecoder implements JwtDecoder {

    private final KycUserTokenSessionService service;

    @Override
    public Jwt decode(String token) throws JwtException {
        try{
            return service.retrieveInfoUserToken(token);
        }
        catch(KycRestException ex){
            throw new BadJwtException("Bad Token",ex);
        }
    }
}
