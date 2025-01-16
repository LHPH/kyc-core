package com.kyc.core.security;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.MessageData;
import com.kyc.core.util.CryptoUtil;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.ProviderException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

public class Aes256GcmCipherOperation implements CipherOperation<SecretKey,SecretKey> {

    private static final String ENCRYPT_ALG = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 16;
    private static final int AES_KEY_BIT = 256;

    private final SecretKey secretKey;

    public Aes256GcmCipherOperation(){
        this(CryptoUtil.getAesKey(AES_KEY_BIT));
    }

    public Aes256GcmCipherOperation(SecretKey secretKey){
        this.secretKey = secretKey;
    }

    @Override
    public String encrypt(String plainText, SecretKey key) {
        byte[] encryptedText = encryptWithIv(plainText,key,CryptoUtil.getRandomNonce(IV_LENGTH_BYTE));
        return Base64.getEncoder().encodeToString(encryptedText);
    }

    @Override
    public String decrypt(String encryptedText, SecretKey key) {

        byte [] bytesEncryptedText = Base64.getDecoder().decode(encryptedText);
        return new String(decryptWithIv(bytesEncryptedText,key),StandardCharsets.UTF_8);
    }

    protected byte [] encrypt(String plainText, SecretKey key, byte [] iv){
       try{

           Cipher cipher = Cipher.getInstance(ENCRYPT_ALG);
           cipher.init(Cipher.ENCRYPT_MODE,key,new GCMParameterSpec(TAG_LENGTH_BIT,iv));

           return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
       }
       catch(GeneralSecurityException | ProviderException ex){

           throw KycException.builder()
                   .errorData(new MessageData())
                   .exception(ex)
                   .build();
       }
    }

    protected byte[] encryptWithIv(String plainText, SecretKey key, byte [] iv){

        byte[] encryptedText = encrypt(plainText,key,iv);

        return ByteBuffer.allocate(iv.length + encryptedText.length)
                .put(iv)
                .put(encryptedText)
                .array();
    }

    protected byte [] decrypt(byte[] encryptedText, SecretKey key, byte[] iv){

        try{
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALG);
            cipher.init(Cipher.DECRYPT_MODE,key,new GCMParameterSpec(TAG_LENGTH_BIT,iv));

            return cipher.doFinal(encryptedText);
        }
        catch(GeneralSecurityException | ProviderException ex){

            throw KycException.builder()
                    .errorData(new MessageData())
                    .exception(ex)
                    .build();
        }
    }

    protected byte [] decryptWithIv(byte [] encryptedText, SecretKey key){

        ByteBuffer bb = ByteBuffer.wrap(encryptedText);
        byte[] iv = new byte[IV_LENGTH_BYTE];
        bb.get(iv);

        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);

        return decrypt(cipherText, key, iv);
    }

    @Override
    public String encrypt(String plainText){
        Assert.notNull(this.secretKey,"SecretKey must not be null");
        return encrypt(plainText,this.secretKey);
    }

    @Override
    public String decrypt(String plainText){
        Assert.notNull(this.secretKey,"SecretKey must not be null");
        return decrypt(plainText,this.secretKey);
    }
}
