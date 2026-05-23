package com.kyc.core.config;

import com.kyc.core.security.RsaCipherOperation;
import com.kyc.core.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.security.KeyPair;

@Configuration
public class RsaKeyStoreConfig {

    @Value("${kyc-config.encryption.rsa.key-store}")
    private Resource resource;

    @Value("${kyc-config.encryption.rsa.key-alias}")
    private String keyAlias;

    @Value("${kyc-config.encryption.rsa.key-password}")
    private String keyPassword;

    @Value("${kyc-config.encryption.rsa.key-store-type}")
    private String keyStoreType;

    @Value("${kyc-config.encryption.rsa.key-store-password}")
    private String keyStorePassword;

    @Bean
    public RsaCipherOperation rsaCipherOperation() throws Exception{

        KeyPair keyPair = CryptoUtil.loadRsaKeysFromKeystore(resource,keyStorePassword,keyAlias,keyPassword,keyStoreType);
        return new RsaCipherOperation(keyPair);
    }
}
