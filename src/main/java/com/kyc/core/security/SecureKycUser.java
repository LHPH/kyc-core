package com.kyc.core.security;

import com.kyc.core.enums.KycUserTypeEnum;
import com.kyc.core.model.jwt.JwtData;
import com.kyc.core.persistence.entity.KycUser;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Getter
public class SecureKycUser extends User {

    private final Long id;

    public SecureKycUser(KycUser kycUser){
        super(kycUser.getUsername(),
                kycUser.getSecret(),
                kycUser.getActive(),
                true,
                true,
                !kycUser.getLocked(),
                AuthorityUtils.createAuthorityList(kycUser.getUserType().getDescription()));
        this.id = kycUser.getId();
    }

    public SecureKycUser(JwtData jwtData){
        super(String.format("TOKEN_%s",jwtData.getOwner()),
                jwtData.getSid(),
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList(jwtData.getRole())
        );
        this.id = jwtData.getUser();
    }

    public KycUserTypeEnum getUserType(){

        return this.getAuthorities().stream()
                .findFirst()
                .map(ga -> KycUserTypeEnum.getInstance(ga.getAuthority()))
                .orElse(KycUserTypeEnum.UNKNOWN);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("id=").append(id);
        sb.append("ROLE=").append(this.getUserType()).append(",");
        sb.append('}');
        return sb.toString();
    }
}
