package com.kyc.core.security;

public interface CipherFacade {

    String encrypt(String plainText);

    String decrypt(String encryptedText);
}
