package com.kyc.core.soap.security;

import com.kyc.core.model.jwt.JwtData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.security.auth.callback.Callback;

@Getter
@RequiredArgsConstructor
public class WSJwtCallback implements Callback {

    private final JwtData jwtData;
}
