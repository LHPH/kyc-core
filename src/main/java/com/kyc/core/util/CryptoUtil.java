package com.kyc.core.util;

import org.springframework.core.io.Resource;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class CryptoUtil {

    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    public static SecretKey getAesKey(int keySize){
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keySize,SecureRandom.getInstanceStrong());
            return keyGenerator.generateKey();
        }
        catch(NoSuchAlgorithmException ex){
            return null;
        }
    }

    public static KeyPair getKeyPair(int keySize){
        try {
            KeyPairGenerator keyGenerator= KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(keySize);
            return keyGenerator.generateKeyPair();
        }
        catch (NoSuchAlgorithmException ex){
            return null;
        }
    }

    public static SecretKey getAesKeyFromSecret(char [] secret,byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(secret,salt,65536,256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(),"AES");
    }

    public static KeyPair generateRsaKeys(int keySize) throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    public static SecretKey loadAesKeyFromKeystore(Resource resource,
                                                   String secret,
                                                   String keyName,
                                                   String keySecret,
                                                   String type) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableEntryException {


        try(InputStream in = resource.getInputStream()){

            KeyStore keyStore = KeyStore.getInstance(type);
            keyStore.load(in,secret.toCharArray());

            if(!keyStore.containsAlias(keyName)){
                throw new UnrecoverableEntryException("No key with name "+keyName);
            }

            Key key = keyStore.getKey(keyName,keySecret.toCharArray());
            return new SecretKeySpec(key.getEncoded(),"AES");
        }
    }

    public static KeyPair loadRsaKeysFromKeystore(Resource resource,
                                                              String secret,
                                                              String keyName,
                                                              String keySecret,
                                                              String type) throws IOException, KeyStoreException, UnrecoverableEntryException, CertificateException, NoSuchAlgorithmException {

        try(InputStream in = resource.getInputStream()){

            KeyStore keyStore = KeyStore.getInstance(type);
            keyStore.load(in,secret.toCharArray());

            if(!keyStore.containsAlias(keyName)){
                throw new UnrecoverableEntryException("No key with name "+keyName);
            }

            PrivateKey privateKey =(PrivateKey) keyStore.getKey(keyName,keySecret.toCharArray());

            Certificate cert = keyStore.getCertificate(keyName);
            PublicKey publicKey = cert.getPublicKey();

            return new KeyPair(publicKey,privateKey);
        }
    }
}
