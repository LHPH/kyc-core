package com.kyc.core.util;

import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.jwt.JWTData;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

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
                .claim("key",data.getKey())
                .claim("channel",data.getChannel())
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
            data.setKey(Objects.toString(claimsSet.getClaim("key"),null));
            data.setChannel(Objects.toString(claimsSet.getClaim("channel"),null));
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

    public static String extractTokenFromAuthHeader(String token){

        boolean cond1 = StringUtils.isNotBlank(token);
        boolean cond2 = StringUtils.startsWith(token,"Bearer ");

        if(BooleanUtils.and(new boolean[]{cond1,cond2})){

            return token.replace("Bearer ","");
        }
        throw new OAuth2AuthenticationException("Invalid Token");
    }

    private TokenUtil(){}
}
