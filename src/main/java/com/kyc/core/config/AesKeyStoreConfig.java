package com.kyc.core.config;

import com.kyc.core.security.Aes256GcmCipherOperation;
import com.kyc.core.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.crypto.SecretKey;

@Configuration
public class AesKeyStoreConfig {

    @Value("${kyc-config.encryption.aes.key-store}")
    private Resource resource;

    @Value("${kyc-config.encryption.aes.key-alias}")
    private String keyAlias;

    @Value("${kyc-config.encryption.aes.key-password}")
    private String keyPassword;

    @Value("${kyc-config.encryption.aes.key-store-type}")
    private String keyStoreType;

    @Value("${kyc-config.encryption.aes.key-store-password}")
    private String keyStorePassword;


    @Bean
    public Aes256GcmCipherOperation aesCipherOperation() throws Exception{

        SecretKey secretKey = CryptoUtil.loadAesKeyFromKeystore(resource,keyStorePassword,keyAlias,keyPassword,keyStoreType);
        return new Aes256GcmCipherOperation(secretKey);
    }

}
