package com.kyc.core.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

@RunWith(MockitoJUnitRunner.class)
public class PasswordFormatValidationServiceTest {

    @Test
    public void validatePassword_checkingGoodPassWithDefaultValidator_returnSuccess(){

        PasswordFormatValidationService service = new PasswordFormatValidationService();

        PasswordData data = new PasswordData();
        data.setUsername("test");
        data.setPassword("Pa$$w0rd");

        RuleResult result = service.validatePassword(data);
        Assert.assertTrue(result.isValid());
    }

    @Test
    public void validatePassword_checkingBadPassWithDefaultValidator_returnFailure(){

        PasswordFormatValidationService service = new PasswordFormatValidationService();

        PasswordData data = new PasswordData();
        data.setUsername("test");
        data.setPassword("12345");

        RuleResult result = service.validatePassword(data);
        Assert.assertFalse(result.isValid());
    }

    @Test
    public void validatePassword_checkingGoodPassWithCustomValidator_returnSuccess(){

        PasswordValidator validator = new PasswordValidator(new LengthRule(15));
        PasswordFormatValidationService service = new PasswordFormatValidationService(validator);

        PasswordData data = new PasswordData();
        data.setUsername("test");
        data.setPassword("12345abcde0oi90");

        RuleResult result = service.validatePassword(data);
        Assert.assertTrue(result.isValid());
    }
}
