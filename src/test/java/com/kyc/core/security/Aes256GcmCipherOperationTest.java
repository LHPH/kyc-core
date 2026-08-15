package com.kyc.core.security;

import com.kyc.core.exception.KycException;
import com.kyc.core.util.CryptoUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;

@ExtendWith(MockitoExtension.class)
public class Aes256GcmCipherOperationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Aes256GcmCipherOperationTest.class);

    private final CipherOperation<SecretKey,SecretKey> cipherTextOperation = new Aes256GcmCipherOperation();

    private SecretKey secretKey = CryptoUtil.getAesKey(256);

    @Test
    public void encrypt_encryptingText_returnEncryptedText(){

        String result = cipherTextOperation.encrypt("test",secretKey);
        LOGGER.info("{}",result);
        Assertions.assertNotNull(result);
    }

    @Test
    public void decrypt_decryptingText_returnPlainText(){

        String result = cipherTextOperation.encrypt("test",secretKey);
        LOGGER.info("{}",result);
        Assertions.assertNotNull(result);

        result = cipherTextOperation.decrypt(result,secretKey);
        LOGGER.info("{}",result);
        Assertions.assertNotNull(result);
    }

    @Test
    public void decrypt_decryptingTextButBadIv_throwException(){

        Assertions.assertThrows(KycException.class,()->{
            cipherTextOperation.decrypt("3qtKTc/iiGxukfcfDju+jL8AonU=",secretKey);
        });
    }

}
