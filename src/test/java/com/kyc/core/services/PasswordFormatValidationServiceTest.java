package com.kyc.core.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.passay.DefaultPasswordValidator;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.ValidationResult;
import org.passay.rule.LengthRule;

@RunWith(MockitoJUnitRunner.class)
public class PasswordFormatValidationServiceTest {

    @Test
    public void validatePassword_checkingGoodPassWithDefaultValidator_returnSuccess(){

        PasswordFormatValidationService service = new PasswordFormatValidationService();

        PasswordData data = new PasswordData("test","Pa$$w0rd");

        ValidationResult result = service.validatePassword(data);
        Assert.assertTrue(result.isValid());
    }

    @Test
    public void validatePassword_checkingBadPassWithDefaultValidator_returnFailure(){

        PasswordFormatValidationService service = new PasswordFormatValidationService();

        PasswordData data = new PasswordData("test","12345");

        ValidationResult result = service.validatePassword(data);
        Assert.assertFalse(result.isValid());
    }

    @Test
    public void validatePassword_checkingGoodPassWithCustomValidator_returnSuccess(){

        PasswordValidator validator = new DefaultPasswordValidator(new LengthRule(15));
        PasswordFormatValidationService service = new PasswordFormatValidationService(validator);

        PasswordData data = new PasswordData("test","12345abcde0oi90");

        ValidationResult result = service.validatePassword(data);
        Assert.assertTrue(result.isValid());
    }
}
