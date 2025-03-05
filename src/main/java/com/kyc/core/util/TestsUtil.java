package com.kyc.core.util;

import com.kyc.core.model.jwt.JwtData;
import com.kyc.core.model.web.ResponseData;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class TestsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestsUtil.class);

    public static <T> ResponseEntity<ResponseData<T>> getResponseTest(T body, HttpStatus httpStatus){

        ResponseData<T> responseData = ResponseData.of(body,httpStatus);
        return responseData.toResponseEntity();
    }

    public static <T> ResponseEntity<ResponseData<T>> getResponseTest(T body){
        return getResponseTest(body,HttpStatus.OK);
    }

    public static List<String> checkPDAcroFormForms(String pathFile){

        ClassPathResource cl = new ClassPathResource(pathFile);
        List<String> list = new ArrayList<>();

        try(InputStream in  = cl.getInputStream()){

            PDDocument pDDocument = PDDocument.load(in);

            PDAcroForm acroForm = pDDocument.getDocumentCatalog().getAcroForm();

            for(PDField field : acroForm.getFields()){

                list.add(field.getFullyQualifiedName());
                LOGGER.info("{}",field.getFullyQualifiedName());
            }
            pDDocument.close();
        }
        catch(IOException ex){
            LOGGER.error(" ",ex);
            return null;
        }
        return list;
    }

    public static JwtData getJwtData(){

        return JwtData.builder()
                .channel("ONLINE")
                .owner(1L)
                .role("CUSTOMER")
                .exp(System.currentTimeMillis()+50000)
                .iat(System.currentTimeMillis())
                .sub("SUB")
                .iss("ISSUER")
                .addAud("http://localhost:9000")
                .header("alg","HMAC256")
                .addition("claim","value")
                .user(1L)
                .build();
    }

    public static Jwt getJwt(){

        return Jwt.withTokenValue("test")
                .header("alg","HMAC256")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(5000))
                .issuer("ISSUER")
                .audience(Collections.singletonList("http://localhost:9000"))
                .claim("claim","value")
                .claim("channel","ONLINE")
                .claim("role","CUSTOMER")
                .claim("user","1")
                .subject("SUB")
                .build();
    }

    private TestsUtil(){}
}
