package com.kyc.core.enums;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum KycUserTypeEnum {

    UNKNOWN(0),
    CUSTOMER(1),
    EXECUTIVE(2),
    SYSTEM(3);

    private final Integer id;

    public static KycUserTypeEnum getInstance(Integer id){

        KycUserTypeEnum result = UNKNOWN;

        for(KycUserTypeEnum value : KycUserTypeEnum.values()){

            if(value.getId().equals(id)){
                result = value;
                break;
            }
        }
        return result;
    }

    public static KycUserTypeEnum getInstance(String desc){

        KycUserTypeEnum result = UNKNOWN;

        for(KycUserTypeEnum value : KycUserTypeEnum.values()){

            if(value.name().equalsIgnoreCase(desc)){
                result = value;
                break;
            }
        }
        return result;
    }
}
