package com.kyc.core.security;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.MessageData;
import lombok.Getter;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;

@Getter
public class RsaCipherOperation implements CipherOperation<PublicKey,PrivateKey> {

    public static final String ENCRYPT_ALG = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    public static final int RSA_KEY_BIT = 2048;
    private final OAEPParameterSpec oaepParams;

    public RsaCipherOperation(){

        this.oaepParams = new OAEPParameterSpec(
                "SHA-256",
                "MGF1",
                new MGF1ParameterSpec("SHA-256"), // Explicitly set MGF1 to SHA-256
                PSource.PSpecified.DEFAULT
        );
    }

    @Override
    public String encrypt(String plainText, PublicKey publicKey) {

        try{
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALG);
            cipher.init(Cipher.ENCRYPT_MODE,publicKey,this.oaepParams);

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
            cipher.init(Cipher.DECRYPT_MODE,privateKey,this.oaepParams);

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
