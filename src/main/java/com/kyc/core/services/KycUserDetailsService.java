package com.kyc.core.services;

import com.kyc.core.persistence.entity.KycUser;
import com.kyc.core.persistence.repositories.KycUserRepository;
import com.kyc.core.security.SecureKycUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;



public class KycUserDetailsService implements UserDetailsService {

    @Autowired
    private KycUserRepository kycUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try{

            Optional<KycUser> opUser = kycUserRepository.findByUsername(username);
            if(opUser.isPresent()){

                KycUser kycUser = opUser.get();
                return new SecureKycUser(kycUser);
            }
            throw new UsernameNotFoundException("No found "+username);
        }
        catch(DataAccessException ex){
            throw new UsernameNotFoundException("Service unavailable for "+username,ex);
        }
    }
}
