package com.kyc.core.persistence.repositories;

import com.kyc.core.persistence.entity.KycParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KycParameterRepository extends JpaRepository<KycParameter,Long> {

    @Query(nativeQuery = true,name = "KycParameter.getKey")
    Optional<KycParameter> getKey(String key);
}
