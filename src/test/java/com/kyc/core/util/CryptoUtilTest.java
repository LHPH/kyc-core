package com.kyc.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@ExtendWith(MockitoExtension.class)
public class CryptoUtilTest {

    @Test
    public void getRandomNonce_gettingRandomArrayWithSpecificLength_returnArrayWithSpecifiedLength(){

        byte [] arr = CryptoUtil.getRandomNonce(16);
        Assertions.assertNotNull(arr);
        Assertions.assertEquals(16,arr.length);
    }

    @Test
    public void getAesKey_getAesKey_gettingNewAesKey(){

        Assertions.assertNotNull(CryptoUtil.getAesKey(128));
    }

    @Test
    public void getAesKeyFromSecret_generatingAesKeyWithPass_returnAesKey() throws InvalidKeySpecException, NoSuchAlgorithmException {

        Assertions.assertNotNull(CryptoUtil.getAesKeyFromSecret("secret".toCharArray(),"salt".getBytes()));
    }

    @Test
    public void generateRsaKeys_generatingKeyPair_returnNewRsaKeys() throws NoSuchAlgorithmException {

        KeyPair keyPair = CryptoUtil.generateRsaKeys(2048);
        Assertions.assertNotNull(keyPair);
        Assertions.assertNotNull(keyPair.getPrivate());
        Assertions.assertNotNull(keyPair.getPublic());
    }

}
