package com.kyc.core.util;

import com.kyc.core.model.jwt.JwtData;
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
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.kyc.core.constants.TokenConstants.JWT_CLAIM_CHANNEL;
import static com.kyc.core.constants.TokenConstants.JWT_CLAIM_OWNER;
import static com.kyc.core.constants.TokenConstants.JWT_CLAIM_ROLE;
import static com.kyc.core.constants.TokenConstants.JWT_CLAIM_USER;
import static com.kyc.core.util.DateUtil.dateToMilliseconds;
import static com.kyc.core.util.DateUtil.instantToMilliseconds;
import static com.kyc.core.util.DateUtil.millisToDate;
import static com.kyc.core.util.DateUtil.millisecondsToInstant;
import static com.kyc.core.util.GeneralUtil.convertOrNull;

public final class TokenUtil {

    private static final Set<String> DUPLICATE_CLAIMS;

    static{

        Set<String> aux = new HashSet<>();
        aux.add(JWT_CLAIM_OWNER);
        aux.add(JWT_CLAIM_USER);
        aux.add(JWT_CLAIM_CHANNEL);
        aux.add(JWT_CLAIM_ROLE);
        aux.addAll(JWTClaimsSet.getRegisteredNames());

        DUPLICATE_CLAIMS = Collections.unmodifiableSet(aux);
    }

    public static byte [] generateRandomSharedSecret(int length) {

        SecureRandom random = new SecureRandom();
        byte[] sharedSecret = new byte[length];
        random.nextBytes(sharedSecret);
        return sharedSecret;
    }

    public static String getToken(JwtData data, JWSAlgorithm algorithm, byte [] sharedSecret) throws JOSEException, ParseException {

        Date exp = millisToDate(data.getExp());
        Date iat = millisToDate(data.getIat());

       JWSSigner signer = new MACSigner(sharedSecret);

        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        for(Map.Entry<String,Object> entry : data.getAdditions().entrySet()){

            builder = builder.claim(entry.getKey(),entry.getValue());
        }

        JWTClaimsSet claimsSet = builder
                .claim(JWT_CLAIM_OWNER,data.getOwner())
                .claim(JWT_CLAIM_USER,data.getUser())
                .claim(JWT_CLAIM_CHANNEL,data.getChannel())
                .claim(JWT_CLAIM_ROLE,data.getRole())
                .subject(data.getSub())
                .issuer(data.getIss())
                .audience(data.getAud())
                .expirationTime(exp)
                .issueTime(iat)
                .serializeNullClaims(false)
                .build();

        Map<String,Object> headers = new HashMap<>();
        headers.put("alg",algorithm.getName());
        headers.putAll(ObjectUtils.defaultIfNull(data.getHeaders(),new HashMap<>()));

        JWSHeader jwsHeader = JWSHeader.parse(headers);

        SignedJWT signedJWT = new SignedJWT(jwsHeader,claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    public static JwtData getJwtData(String token, JWSAlgorithm algorithm, byte [] sharedSecret) throws ParseException, JOSEException {

        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(sharedSecret);

        if(signedJWT.verify(verifier)){

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            Map<String,Object> claims = claimsSet.getClaims()
                    .entrySet()
                    .stream()
                    .filter(excludeDuplicateClaims())
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));

            return JwtData.builder()
                    .owner(convertOrNull(claimsSet.getClaim(JWT_CLAIM_OWNER),Long.class))
                    .user(convertOrNull(claimsSet.getClaim(JWT_CLAIM_USER),Long.class))
                    .channel(Objects.toString(claimsSet.getClaim(JWT_CLAIM_CHANNEL),null))
                    .role(Objects.toString(claimsSet.getClaim(JWT_CLAIM_ROLE),null))
                    .sub(claimsSet.getSubject())
                    .aud(claimsSet.getAudience())
                    .iss(claimsSet.getIssuer())
                    .exp(dateToMilliseconds(claimsSet.getExpirationTime()))
                    .iat(dateToMilliseconds(claimsSet.getIssueTime()))
                    .headers(signedJWT.getHeader().toJSONObject())
                    .additions(claims)
                    .build();
        }
        return null;
    }

    public static Jwt transform(String token, JwtData jwtData){

        return Jwt.withTokenValue(token)
                .subject(jwtData.getSub())
                .issuer(jwtData.getIss())
                .audience(jwtData.getAud())
                .expiresAt(millisecondsToInstant(jwtData.getExp()))
                .issuedAt(millisecondsToInstant(jwtData.getIat()))
                .claims(c -> {

                    c.put(JWT_CLAIM_OWNER,jwtData.getOwner());
                    c.put(JWT_CLAIM_USER,jwtData.getUser());
                    c.put(JWT_CLAIM_CHANNEL,jwtData.getChannel());
                    c.put(JWT_CLAIM_ROLE,jwtData.getRole());
                    c.putAll(jwtData.getAdditions());
                })
                .headers(h -> h.putAll(jwtData.getHeaders()))
                .build();
    }

    public static JwtData transform(Jwt jwt){

        Map<String,Object> claims = jwt.getClaims()
                .entrySet()
                .stream()
                .filter(excludeDuplicateClaims())
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));

        return JwtData.builder()
                .owner(jwt.getClaim(JWT_CLAIM_OWNER))
                .user(jwt.getClaim(JWT_CLAIM_USER))
                .channel(jwt.getClaim(JWT_CLAIM_CHANNEL))
                .role(jwt.getClaim(JWT_CLAIM_ROLE))
                .sub(jwt.getSubject())
                .aud(jwt.getAudience())
                .iss(jwt.getIssuer().toString())
                .exp(instantToMilliseconds(jwt.getExpiresAt()))
                .iat(instantToMilliseconds(jwt.getIssuedAt()))
                .headers(jwt.getHeaders())
                .additions(claims)
                .build();
    }

    public static boolean checkExpirationTime(JwtData data){

        if(data != null && data.getExp()!=null){
            return new Date().before(new Date(data.getExp()));
        }
        return false;
    }

    public static String extractTokenFromAuthHeader(String token){

        boolean cond1 = StringUtils.isNotBlank(token);

        if(cond1){

            return token.replace("Bearer ","");
        }
        throw new OAuth2AuthenticationException("Invalid Token");
    }

    private static Predicate<Map.Entry<String,Object>> excludeDuplicateClaims(){

        return entry -> !DUPLICATE_CLAIMS.contains(entry.getKey());
    }

    private TokenUtil(){}
}
