package com.kyc.core.rest.feign.common;


import com.kyc.core.model.jwt.JwtData;
import com.kyc.core.model.jwt.TokenMetaData;
import com.kyc.core.model.web.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "KYC-USERS")
public interface KycUserClient {

    @GetMapping("/user/session-checking")
    ResponseData<JwtData> sessionChecking(@RequestHeader("Authorization") String bearerToken);

    @PostMapping("/user/session-renewal")
    ResponseData<JwtData> sessionRenewal(@RequestHeader("Authorization") String bearerToken);
}
