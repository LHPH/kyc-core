package com.kyc.core.security;

import com.kyc.core.exception.KycException;
import com.kyc.core.util.CryptoUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

@ExtendWith(MockitoExtension.class)
public class RsaCipherOperationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Aes256GcmCipherOperationTest.class);

    private final CipherOperation<PublicKey, PrivateKey> cipherTextOperation = new RsaCipherOperation();

    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    @BeforeAll
    public static void setUp() throws NoSuchAlgorithmException {

        KeyPair keyPair = CryptoUtil.generateRsaKeys(2048);
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }

    @Test
    public void encrypt_encryptingText_returnEncryptedText(){

        String result = cipherTextOperation.encrypt("test",publicKey);
        LOGGER.info("{}",result);
        Assertions.assertNotNull(result);
    }

    @Test
    public void decrypt_decryptingText_returnPlainText(){

        String result = cipherTextOperation.encrypt("test",publicKey);
        LOGGER.info("{}",result);
        Assertions.assertNotNull(result);

        result = cipherTextOperation.decrypt(result,privateKey);
        LOGGER.info("{}",result);
        Assertions.assertNotNull(result);
    }

    @Test
    public void encrypt_encryptingTextButBadKey_throwKycException(){

        Assertions.assertThrows(KycException.class,()->{

            CipherOperation<PublicKey, PrivateKey> other = new RsaCipherOperation();
            other.encrypt("test",null);
        });
    }

    @Test
    public void decrypt_decryptingTextButBadIv_throwException(){

        Assertions.assertThrows(KycException.class,()->{
            cipherTextOperation.decrypt("3qtKTc/iiGxukfcfDju+jL8AonU=",privateKey);
        });
    }
}
