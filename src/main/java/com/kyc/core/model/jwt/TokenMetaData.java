package com.kyc.core.model.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TokenMetaData {

    private String sub;
    private String role;
    private String originChannel;
}
