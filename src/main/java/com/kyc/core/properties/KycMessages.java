package com.kyc.core.properties;

import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.BaseModel;
import com.kyc.core.model.properties.CatalogMessages;
import com.kyc.core.model.web.MessageData;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.kyc.core.util.GeneralUtil.isNotNullObjectAndAttribute;

@ConfigurationProperties(prefix = "kyc-messages")
@Getter
@Setter
public class KycMessages extends BaseModel {

    private static final Logger LOGGER = LogManager.getLogger(KycMessages.class);

    private CatalogMessages catalog;

    public MessageData getMessage(String code){

        if(isNotNullObjectAndAttribute(catalog, CatalogMessages::getMessages)){

            return catalog.getMessages()
                    .getOrDefault(code,new MessageData());
        }
        LOGGER.warn("No messages {}",catalog);
        return new MessageData();
    }
}
