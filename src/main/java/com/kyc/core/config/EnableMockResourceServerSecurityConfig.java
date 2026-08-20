package com.kyc.core.config;

import com.kyc.core.security.jwt.KycUserSessionTokenJwtDecoder;
import com.kyc.core.security.jwt.KycUserTokenSessionService;
import com.kyc.core.services.DefaultKycUserTokenSessionService;
import com.kyc.core.services.mock.MockKycUserTokenSessionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnProperty(name = "kyc-config.mock.resource-server.enabled",havingValue = "true")
public class EnableMockResourceServerSecurityConfig {

    @Bean
    public KycUserTokenSessionService kycUserTokenSessionService(){
        return new MockKycUserTokenSessionService();
    }
}
