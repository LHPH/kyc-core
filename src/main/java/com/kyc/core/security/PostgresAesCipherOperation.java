package com.kyc.core.security;

import com.kyc.core.exception.KycException;
import com.kyc.core.model.web.MessageData;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.crypto.SecretKey;
import java.util.Base64;

public class PostgresAesCipherOperation implements CipherOperation<SecretKey,SecretKey> {

    private JdbcTemplate jdbcTemplate;

    public PostgresAesCipherOperation(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public String encrypt(String plainText, SecretKey key) {

        try{

            String sql = "SELECT ENCODE(pgp_sym_encrypt(?,?,'cipher-algo=aes256'),'BASE64') AS ENCRYPTED_TEXT;";
            String secret = Base64.getEncoder().encodeToString(key.getEncoded());
            //String secret = new String(key.getEncoded(), StandardCharsets.UTF_8);

            return jdbcTemplate.queryForObject(sql,String.class,plainText,secret);
        }
        catch (DataAccessException ex){

            throw KycException.builder()
                    .errorData(new MessageData())
                    .exception(ex)
                    .build();
        }
    }

    @Override
    public String decrypt(String encryptedText, SecretKey key) {
        try{

            String sql = "SELECT pgp_sym_decrypt(DECODE(?,'BASE64'),?,'cipher-algo=aes256') AS PLAIN_TEXT";
            String secret = Base64.getEncoder().encodeToString(key.getEncoded());

            return jdbcTemplate.queryForObject(sql,String.class,encryptedText,secret);
        }
        catch (DataAccessException ex){

            throw KycException.builder()
                    .errorData(new MessageData())
                    .exception(ex)
                    .build();
        }
    }
}
