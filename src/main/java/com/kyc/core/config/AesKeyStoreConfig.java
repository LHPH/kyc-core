package com.kyc.core.config;

import com.kyc.core.model.properties.KeyStoreDataProps;
import com.kyc.core.security.Aes256GcmCipherOperation;
import com.kyc.core.security.AesCipherFacade;
import com.kyc.core.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.crypto.SecretKey;

@Configuration
public class AesKeyStoreConfig {

    @Bean
    public Aes256GcmCipherOperation aesCipherOperation(){

        return new Aes256GcmCipherOperation();
    }

    @Bean(name = "aesKeyStoreDataProps")
    @ConditionalOnProperty(name = "kyc-config.encryption.aes.enabled", havingValue = "true")
    @ConfigurationProperties(prefix = "kyc-config.encryption.aes")
    public KeyStoreDataProps aesKeyStoreDataProps(){
        return new KeyStoreDataProps();
    }

    @Bean
    @ConditionalOnProperty(name = "kyc-config.encryption.aes.enabled", havingValue = "true")
    public AesCipherFacade aesCipherFacade(
            Aes256GcmCipherOperation aes256GcmCipherOperation,
            @Qualifier("aesKeyStoreDataProps") KeyStoreDataProps aesKeyStoreDataProps
            ) throws Exception{

        Resource resource = aesKeyStoreDataProps.getKeyStore();
        String keyStorePassword = aesKeyStoreDataProps.getKeyStorePassword();
        String keyAlias = aesKeyStoreDataProps.getKeyAlias();
        String keyPassword = aesKeyStoreDataProps.getKeyPassword();
        String keyStoreType = aesKeyStoreDataProps.getKeyStoreType();

        SecretKey secretKey = CryptoUtil.loadAesKeyFromKeystore(resource,keyStorePassword,keyAlias,keyPassword,keyStoreType);
        return new AesCipherFacade(secretKey,aes256GcmCipherOperation);
    }

}
