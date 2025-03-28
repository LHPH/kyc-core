package com.kyc.core.model.jwt;

import com.kyc.core.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class JwtData extends BaseModel {

    private Long owner;
    private Long user;
    private String channel;
    private String role;
    private String scope;

    private String sub;
    private String iss;
    @Singular(value = "addAud")
    private List<String> aud = new ArrayList<>();
    private Long iat;
    private Long exp;

    @Singular
    private Map<String, Object> additions = new HashMap<>();

    @Singular
    private Map<String, Object> headers = new HashMap<>();
}
