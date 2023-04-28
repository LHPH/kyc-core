package com.kyc.core.security;

import com.kyc.core.exception.KycException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostgresAesCipherOperationTest {

    @Mock
    private SecretKey secretKey;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private CipherOperation<SecretKey,SecretKey> cipherOperation;

    @BeforeAll
    public static void setUp(){
        MockitoAnnotations.openMocks(PostgresAesCipherOperationTest.class);
    }

    @BeforeEach
    public void init(){
        cipherOperation = new PostgresAesCipherOperation(jdbcTemplate);
    }

    @Test
    public void encrypt_encryptingText_returnEncryptedText(){

        when(secretKey.getEncoded())
                .thenReturn("key".getBytes(StandardCharsets.UTF_8));
        when(jdbcTemplate.queryForObject(anyString(),any(Class.class),anyString(),anyString()))
                .thenReturn("encrypted");

        String result = cipherOperation.encrypt("test",secretKey);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("encrypted",result);
    }

    @Test
    public void decrypt_decryptingText_returnPlainText(){

        when(secretKey.getEncoded())
                .thenReturn("key".getBytes(StandardCharsets.UTF_8));
        when(jdbcTemplate.queryForObject(anyString(),any(Class.class),anyString(),anyString()))
                .thenReturn("plain");

        String result = cipherOperation.decrypt("encrypted",secretKey);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("plain",result);
    }

    @Test
    public void encrypt_encryptingTextButError_throwKycException(){

        Assertions.assertThrows(KycException.class,()->{

            when(secretKey.getEncoded())
                    .thenReturn("key".getBytes(StandardCharsets.UTF_8));
            doThrow(new InvalidDataAccessResourceUsageException("test db error"))
                    .when(jdbcTemplate).queryForObject(anyString(),any(Class.class),anyString(),anyString());

            cipherOperation.encrypt("test",secretKey);
        });
    }

    @Test
    public void decrypt_decryptingTextButError_throwKycException(){

      Assertions.assertThrows(KycException.class,()->{

          when(secretKey.getEncoded())
                  .thenReturn("key".getBytes(StandardCharsets.UTF_8));
          doThrow(new InvalidDataAccessResourceUsageException("test db error"))
                  .when(jdbcTemplate).queryForObject(anyString(),any(Class.class),anyString(),anyString());

          cipherOperation.decrypt("encrypted",secretKey);
      });
    }
}
