package com.kyc.core.security;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.web.MessageData;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RsaCipherOperation implements CipherOperation<PublicKey,PrivateKey> {

    private static final String ENCRYPT_ALG = "RSA";

    @Override
    public String encrypt(String plainText, PublicKey publicKey) {

        try{
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALG);

            cipher.init(Cipher.ENCRYPT_MODE,publicKey);

            byte [] encryptedText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedText);
        }
        catch(GeneralSecurityException ex){
            throw KycException.builder()
                    .errorData(new MessageData())
                    .exception(ex)
                    .build();
        }
    }

    @Override
    public String decrypt(String encryptedText, PrivateKey privateKey) {

        try{
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALG);
            cipher.init(Cipher.DECRYPT_MODE,privateKey);

            byte[] bytesEncryptedText = Base64.getDecoder().decode(encryptedText);
            byte[] plainText = cipher.doFinal(bytesEncryptedText);

            return new String(plainText,StandardCharsets.UTF_8);
        }
        catch(GeneralSecurityException ex){
            throw KycException.builder()
                    .errorData(new MessageData())
                    .exception(ex)
                    .build();
        }
    }
}
