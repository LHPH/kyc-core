package com.kyc.core.security;

import com.kyc.core.util.CryptoUtil;
import lombok.Getter;
import org.springframework.util.Assert;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

import static com.kyc.core.security.RsaCipherOperation.RSA_KEY_BIT;

@Getter
public class RsaCipherFacade implements CipherFacade{

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final RsaCipherOperation rsaCipherOperation;

    public RsaCipherFacade(){
        this(Objects.requireNonNull(CryptoUtil.getKeyPair(RSA_KEY_BIT)), new RsaCipherOperation());
    }

    public RsaCipherFacade(KeyPair keyPair, RsaCipherOperation rsaCipherOperation){
        this(keyPair.getPrivate(), keyPair.getPublic(), rsaCipherOperation);
    }

    public RsaCipherFacade(PrivateKey privateKey, PublicKey publicKey, RsaCipherOperation rsaCipherOperation){
        Assert.notNull(privateKey,"Private key must not be null");
        Assert.notNull(publicKey, "Public key must not be null");
        Assert.notNull(publicKey, "rsaCipherOperation must not be null");
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.rsaCipherOperation = rsaCipherOperation;
    }

    @Override
    public String encrypt(String plainText) {

        return this.rsaCipherOperation.encrypt(plainText, this.publicKey);
    }

    @Override
    public String decrypt(String encryptedText) {
        return this.rsaCipherOperation.decrypt(encryptedText, this.privateKey);
    }
}
