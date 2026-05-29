package com.kyc.core.security;

import com.kyc.core.util.CryptoUtil;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.crypto.SecretKey;

import static com.kyc.core.security.Aes256GcmCipherOperation.AES_KEY_BIT;

@Getter
public class AesCipherFacade implements CipherFacade{

    private final SecretKey secretKey;
    private final Aes256GcmCipherOperation aes256GcmCipherOperation;

    public AesCipherFacade(){
        this(CryptoUtil.getAesKey(AES_KEY_BIT), new Aes256GcmCipherOperation());
    }

    public AesCipherFacade(SecretKey secretKey, Aes256GcmCipherOperation aes256GcmCipherOperation){
        this.secretKey = secretKey;
        this.aes256GcmCipherOperation = aes256GcmCipherOperation;
    }

    @Override
    public String encrypt(String plainText){
        Assert.notNull(this.secretKey,"SecretKey must not be null");
        return this.aes256GcmCipherOperation.encrypt(plainText,this.secretKey);
    }

    @Override
    public String decrypt(String plainText){
        Assert.notNull(this.secretKey,"SecretKey must not be null");
        return this.aes256GcmCipherOperation.decrypt(plainText,this.secretKey);
    }
}
