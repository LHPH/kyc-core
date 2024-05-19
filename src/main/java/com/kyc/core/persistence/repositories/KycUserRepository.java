package com.kyc.core.persistence.repositories;

import com.kyc.core.persistence.entity.KycUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KycUserRepository extends JpaRepository<KycUser, Long> {

    Optional<KycUser> findByUsername(String username);

    Optional<KycUser> findByUsernameAndActiveTrue(String username);
}
