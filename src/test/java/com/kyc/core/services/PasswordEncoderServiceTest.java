package com.kyc.core.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class PasswordEncoderServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncoderServiceTest.class);

    @Test
    public void encode_encodingPassWithDefaultEncoder_returnPassEncoded(){

        PasswordEncoderService service = new PasswordEncoderService();
        String encodedString = service.encode("test");
        LOGGER.info("Encoded {}",encodedString);
        Assert.assertTrue(service.matches("test",encodedString));
    }

    @Test
    public void encode_encodingPassWithOtherEncoder_returnPassEncoded(){

        PasswordEncoderService service = new PasswordEncoderService(NoOpPasswordEncoder.getInstance());
        String encodedString = service.encode("test");
        LOGGER.info("Encoded {}",encodedString);
        Assert.assertTrue(service.matches("test",encodedString));
    }

    @Test
    public void match_verifyPass_returnFalse(){

        PasswordEncoderService service = new PasswordEncoderService();
        String encodedString = service.encode("test");
        Assert.assertFalse(service.matches("test2",encodedString));
    }


}
