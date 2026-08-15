package com.kyc.core.services;

import org.passay.DefaultPasswordValidator;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.ValidationResult;
import org.passay.data.EnglishCharacterData;
import org.passay.rule.CharacterCharacteristicsRule;
import org.passay.rule.CharacterRule;
import org.passay.rule.LengthRule;
import org.passay.rule.UsernameRule;
import org.springframework.stereotype.Service;

@Service
public class PasswordFormatValidationService {

    private PasswordValidator validator;

    public PasswordFormatValidationService(){

        CharacterCharacteristicsRule characterRule = new CharacterCharacteristicsRule(
                3,
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit),
                new CharacterRule(EnglishCharacterData.Special)
        );

        UsernameRule usernameRule = new UsernameRule(false,true);

        LengthRule lengthRule = new LengthRule(8,15);

        validator = new DefaultPasswordValidator(characterRule,usernameRule,lengthRule);
    }

    public PasswordFormatValidationService(PasswordValidator passwordValidator){
        this.validator = passwordValidator;
    }

    public ValidationResult validatePassword(PasswordData passwordData){

        return validator.validate(passwordData);
    }

}
