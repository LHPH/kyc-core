package com.kyc.core.services;

import com.kyc.core.persistence.entity.KycUser;
import com.kyc.core.persistence.repositories.KycUserRepository;
import com.kyc.core.security.SecureKycUser;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class KycUserDetailsByIdService implements UserDetailsService {

    @Autowired
    private KycUserRepository kycUserRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        try{

            Optional<KycUser> opUser = kycUserRepository.findById(NumberUtils.toLong(id));
            if(opUser.isPresent()){

                KycUser kycUser = opUser.get();
                return new SecureKycUser(kycUser);
            }
            throw new UsernameNotFoundException("No found user by id "+id);
        }
        catch(DataAccessException ex){
            throw new UsernameNotFoundException("Service unavailable for user with id "+id,ex);
        }
    }
}

