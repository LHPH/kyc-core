package com.kyc.core.services;


import com.kyc.core.annotations.EncodedMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderService {

    private PasswordEncoder passwordEncoder;

    public PasswordEncoderService(){
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public PasswordEncoderService(PasswordEncoder encoder){
        passwordEncoder = encoder;
    }

    @EncodedMapping
    public String encode(String password){
        return passwordEncoder.encode(password);
    }

    public boolean matches(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword,encodedPassword);
    }
}
