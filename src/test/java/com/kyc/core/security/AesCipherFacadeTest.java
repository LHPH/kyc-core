package com.kyc.core.security;

import com.kyc.core.config.AesKeyStoreConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@Import(value = {AesKeyStoreConfig.class})
@EnableConfigurationProperties
public class AesCipherFacadeTest {

    @Autowired
    private AesCipherFacade aesCipherFacade;

    @Test
    public void encrypt_encryptValueAndDecrypt_successfulFlow(){

        String value = "TEST";
        String encryptedText = aesCipherFacade.encrypt(value);
        log.info("{}",encryptedText);
        String plainText = aesCipherFacade.decrypt(encryptedText);
        log.info("{}",plainText);

        assertEquals(value,plainText);
    }
}
