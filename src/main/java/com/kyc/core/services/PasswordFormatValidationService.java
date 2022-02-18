package com.kyc.core.services;

import org.passay.CharacterCharacteristicsRule;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.UsernameRule;
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

        validator = new PasswordValidator(characterRule,usernameRule,lengthRule);
    }

    public PasswordFormatValidationService(PasswordValidator passwordValidator){
        this.validator = passwordValidator;
    }

    public RuleResult validatePassword(PasswordData passwordData){

        return validator.validate(passwordData);
    }

}
