package com.kyc.core.security;

import com.kyc.core.config.RsaKeyStoreConfig;
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
@Import({RsaKeyStoreConfig.class})
@EnableConfigurationProperties
public class RsaCipherFacadeTest {

    @Autowired
    private RsaCipherFacade rsaCipherFacade;

    @Test
    public void  encrypt_encryptValueAndDecrypt_successfulFlow(){

        String value = "TEST";
        String encryptedText = rsaCipherFacade.encrypt(value);
        log.info("{}",encryptedText);
        String plainText = rsaCipherFacade.decrypt(encryptedText);
        log.info("{}",plainText);

        assertEquals(value,plainText);
    }
}
