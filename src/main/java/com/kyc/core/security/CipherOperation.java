package com.kyc.core.security;

import java.security.Key;

public interface CipherOperation<K1 extends Key,K2 extends Key> {

    String encrypt(String plainText, K1 key);

    String decrypt(String encryptedText, K2 key);
}
