package com.kyc.core.config;

import com.kyc.core.model.properties.KeyStoreDataProps;
import com.kyc.core.security.RsaCipherFacade;
import com.kyc.core.security.RsaCipherOperation;
import com.kyc.core.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.security.KeyPair;

@Configuration
public class RsaKeyStoreConfig {

    @Bean
    public RsaCipherOperation rsaCipherOperation(){

        return new RsaCipherOperation();
    }

    @Bean(name = "rsaKeyStoreDataProps")
    @ConditionalOnProperty(name = "kyc-config.encryption.rsa.enabled", havingValue = "true")
    @ConfigurationProperties(prefix = "kyc-config.encryption.rsa")
    public KeyStoreDataProps rsaKeyStoreDataProps(){
        return new KeyStoreDataProps();
    }

    @Bean
    @ConditionalOnProperty(name = "kyc-config.encryption.rsa.enabled", havingValue = "true")
    public RsaCipherFacade rsaCipherFacade(
            RsaCipherOperation rsaCipherOperation,
            @Qualifier("rsaKeyStoreDataProps") KeyStoreDataProps rsaKeyStoreDataProps
            ) throws Exception{

        Resource resource = rsaKeyStoreDataProps.getKeyStore();
        String keyStorePassword = rsaKeyStoreDataProps.getKeyStorePassword();
        String keyAlias = rsaKeyStoreDataProps.getKeyAlias();
        String keyPassword = rsaKeyStoreDataProps.getKeyPassword();
        String keyStoreType = rsaKeyStoreDataProps.getKeyStoreType();

        KeyPair keyPair = CryptoUtil.loadRsaKeysFromKeystore(resource,keyStorePassword,keyAlias,keyPassword,keyStoreType);
        return new RsaCipherFacade(keyPair,rsaCipherOperation);
    }
}
