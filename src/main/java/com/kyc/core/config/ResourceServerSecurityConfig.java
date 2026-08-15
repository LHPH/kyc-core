package com.kyc.core.config;

import com.kyc.core.exception.handlers.KycRestAuthValidationExceptionHandler;
import com.kyc.core.properties.KycMessages;
import com.kyc.core.security.jwt.BearerTokenAuthenticationEntryPointDelegate;
import com.kyc.core.security.jwt.KycUserSessionTokenJwtDecoder;
import com.kyc.core.security.jwt.KycUserTokenSessionService;
import com.kyc.core.services.DefaultKycUserTokenSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import tools.jackson.databind.json.JsonMapper;

import java.util.Collection;

@EnableWebSecurity
@AutoConfiguration
public class ResourceServerSecurityConfig {

    @Autowired
    private JsonMapper jsonMapper;

    @Autowired
    private KycMessages kycMessages;

    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(CsrfConfigurer::disable);
        http.authorizeHttpRequests((authorize -> {
            authorize.requestMatchers("/actuator/**").permitAll()
                    .anyRequest().authenticated();
        }));
        http.formLogin(FormLoginConfigurer::disable);
        http.httpBasic(HttpBasicConfigurer::disable);
        http.oauth2ResourceServer(customizer -> customizer
                .jwt(Customizer.withDefaults())
                .authenticationEntryPoint(bearerTokenAuthenticationEntryPointDelegate()));
        http.exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(bearerTokenAuthenticationEntryPointDelegate()));
        return http.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public BearerTokenAuthenticationEntryPointDelegate bearerTokenAuthenticationEntryPointDelegate(){
        return new BearerTokenAuthenticationEntryPointDelegate(kycMessages.getMessageByHint("AUTH"),jsonMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public KycUserTokenSessionService kycUserTokenSessionService(){
        return new DefaultKycUserTokenSessionService();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtDecoder jwtDecoder(KycUserTokenSessionService kycUserTokenSessionService){

        return new KycUserSessionTokenJwtDecoder(kycUserTokenSessionService);
    }

    @Bean
    @ConditionalOnMissingBean
    public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("");
        return converter;
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationConverter customJwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return converter;
    }

    @Bean
    @ConditionalOnMissingBean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    @ConditionalOnMissingBean
    public KycRestAuthValidationExceptionHandler kycRestAuthValidationExceptionHandler(){
        return new KycRestAuthValidationExceptionHandler(kycMessages.getMessageByHint("AUTH"));
    }
}
