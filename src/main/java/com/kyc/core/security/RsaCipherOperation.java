package com.kyc.core.security;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.MessageData;
import com.kyc.core.util.CryptoUtil;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Objects;

@Getter
public class RsaCipherOperation implements CipherOperation<PublicKey,PrivateKey> {

    private static final String ENCRYPT_ALG = "RSA";
    private static final int RSA_KEY_BIT = 2048;

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public RsaCipherOperation(){
        this(Objects.requireNonNull(CryptoUtil.getKeyPair(RSA_KEY_BIT)));
    }

    public RsaCipherOperation(KeyPair keyPair){
        this(keyPair.getPrivate(), keyPair.getPublic());
    }

    public RsaCipherOperation(PrivateKey privateKey, PublicKey publicKey){
        Assert.notNull(privateKey,"Private key must not be null");
        Assert.notNull(publicKey, "Public key must not be null");
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

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

    @Override
    public String encrypt(String plainText) {

        return encrypt(plainText, this.publicKey);
    }

    @Override
    public String decrypt(String encryptedText) {
        return decrypt(encryptedText, this.privateKey);
    }
}
