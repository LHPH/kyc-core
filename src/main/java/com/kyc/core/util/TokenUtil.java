package com.kyc.core.util;

import com.kyc.core.model.JWTData;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Date;

public final class TokenUtil {

    public static byte [] generateRandomSharedSecret(int length) {

        SecureRandom random = new SecureRandom();
        byte[] sharedSecret = new byte[length];
        random.nextBytes(sharedSecret);
        return sharedSecret;
    }

    public static String getToken(JWTData data,JWSAlgorithm algorithm, byte [] sharedSecret) throws JOSEException {

        JWSSigner signer = new MACSigner(sharedSecret);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(data.getSubject())
                .issuer(data.getIssuer())
                .audience(data.getAudience())
                .expirationTime(data.getExpirationTime())
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(algorithm),claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    public static JWTData getJwtData(String token,JWSAlgorithm algorithm, byte [] sharedSecret) throws ParseException, JOSEException {

        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(sharedSecret);

        if(signedJWT.verify(verifier)){

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            JWTData data = new JWTData();
            data.setSubject(claimsSet.getSubject());
            data.setAudience(claimsSet.getAudience().get(0));
            data.setIssuer(claimsSet.getIssuer());
            data.setExpirationTime(claimsSet.getExpirationTime());
            return data;

        }
        return null;
    }

    public static boolean checkExpirationTime(JWTData data){

        if(data != null && data.getExpirationTime()!=null){
            return new Date().before(data.getExpirationTime());
        }
        return false;
    }

    private TokenUtil(){}
}
