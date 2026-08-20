package com.kyc.core.services.mock;

import com.kyc.core.enums.KycUserTypeEnum;
import com.kyc.core.enums.MessageType;
import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.MessageData;
import com.kyc.core.security.jwt.KycUserTokenSessionService;
import com.kyc.core.util.TestsUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;

public class MockKycUserTokenSessionService implements KycUserTokenSessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockKycUserTokenSessionService.class);

    @Override
    public Jwt retrieveInfoUserToken(String token) throws KycRestException {

        LOGGER.warn("TOKEN {}",token);

        String [] parts = StringUtils.split(token,"-");

        String inputRole = ArrayUtils.get(parts,0,KycUserTypeEnum.CUSTOMER.name());
        Long userId = NumberUtils.toLong(ArrayUtils.get(parts,1,"1"),1L);
        Long ownerId = NumberUtils.toLong(ArrayUtils.get(parts,2,"1"),1L);

        String role;
        if(Strings.CI.contains(inputRole,KycUserTypeEnum.EXECUTIVE.name())){
            role = KycUserTypeEnum.EXECUTIVE.name();
        }
        else if(Strings.CI.contains(inputRole,KycUserTypeEnum.SYSTEM.name())){
            role = KycUserTypeEnum.SYSTEM.name();
        }
        else if(Strings.CI.contains(inputRole,"ERROR")){

            MessageData messageData = new MessageData("401","Unauthorized", MessageType.ERROR);
            throw KycRestException.builderRestException()
                    .inputData(token)
                    .status(HttpStatus.FORBIDDEN)
                    .errorData(messageData)
                    .build();
        }
        else{
            role = KycUserTypeEnum.CUSTOMER.name();
        }

        LOGGER.warn("ROLE {}, User {}, Owner {}",role,userId,ownerId);

        return TestsUtil.getJwt("TEST",userId,ownerId,role);
    }
}
