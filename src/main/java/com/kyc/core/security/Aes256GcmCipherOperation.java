package com.kyc.core.security;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.MessageData;
import com.kyc.core.util.CryptoUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

public class Aes256GcmCipherOperation implements CipherOperation<SecretKey,SecretKey> {

    private static final String ENCRYPT_ALG = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 16;
    private static final int AES_KEY_BIT = 256;

    private AlgorithmParameterSpec spec;

    public Aes256GcmCipherOperation(){
        this(new GCMParameterSpec(TAG_LENGTH_BIT, CryptoUtil.getRandomNonce(IV_LENGTH_BYTE)));
    }

    public Aes256GcmCipherOperation(AlgorithmParameterSpec spec){
        this(CryptoUtil.getAesKey(AES_KEY_BIT),spec);
    }

    public Aes256GcmCipherOperation(SecretKey secretKey, AlgorithmParameterSpec spec){
        this.spec = spec;
    }

    @Override
    public String encrypt(String plainText, SecretKey key) {
        return encrypt(plainText,key,spec);
    }

    @Override
    public String decrypt(String encryptedText, SecretKey key) {
        return decrypt(encryptedText,key,spec);
    }

    public String encrypt(String plainText, SecretKey key, AlgorithmParameterSpec iv){

       try{

           Cipher cipher = Cipher.getInstance(ENCRYPT_ALG);
           cipher.init(Cipher.ENCRYPT_MODE,key,iv);

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

    public String decrypt(String encryptedText, SecretKey key, AlgorithmParameterSpec iv){

        try{
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALG);
            cipher.init(Cipher.DECRYPT_MODE,key,iv);

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
