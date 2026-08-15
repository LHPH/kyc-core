package com.kyc.core.security.jwt;

import com.kyc.core.exception.KycRestException;
import org.springframework.security.oauth2.jwt.Jwt;

public interface KycUserTokenSessionService {
    Jwt retrieveInfoUserToken(String token) throws KycRestException;
}
