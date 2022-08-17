package com.kyc.core.model.jwt;

import com.kyc.core.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenData extends BaseModel {

    private String token;

    @Override
    public String toString(){
        return "";
    }
}
