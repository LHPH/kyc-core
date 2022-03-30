package com.kyc.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@Profile("dev")
public class DebugInputRequestConfig {

    @Bean
    public CommonsRequestLoggingFilter loggingFilter(){

        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setBeforeMessagePrefix("Before Request: ");
        filter.setIncludeHeaders(true);
        filter.setIncludePayload(true);
        filter.setIncludeQueryString(true);
        filter.setIncludeClientInfo(true);
        filter.setMaxPayloadLength(1000);

        return filter;
    }
}
