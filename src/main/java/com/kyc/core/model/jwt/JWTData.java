package com.kyc.core.model.jwt;

import com.kyc.core.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JWTData extends BaseModel {

    private String subject;
    private String issuer;
    private String audience;
    private Date expirationTime;

    private String key;
    private String channel;
}
