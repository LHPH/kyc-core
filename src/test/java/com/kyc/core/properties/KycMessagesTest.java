package com.kyc.core.properties;

import com.kyc.core.model.properties.CatalogMessages;
import com.kyc.core.model.MessageData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EnableConfigurationProperties(KycMessages.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@ActiveProfiles("test")
public class KycMessagesTest {

    @Autowired
    private KycMessages kycMessages;

    @Test
    public void getMessage_receiveValidCode_returnMessage(){

        MessageData messageData = kycMessages.getMessage("TEST");
        Assert.assertEquals("CODE",messageData.getCode());
    }

    @Test
    public void getMessage_receiveInvalidCode_returnDefaultMessage(){

        MessageData messageData = kycMessages.getMessage("OTHER");
        Assert.assertNull(messageData.getCode());
    }

    @Test
    public void getMessage_noMessages_returnDefaultMessage(){

        CatalogMessages catalogMessages = kycMessages.getCatalog();
        kycMessages.setCatalog(null);

        MessageData messageData = kycMessages.getMessage("TEST");
        Assert.assertNull(messageData.getCode());

        kycMessages.setCatalog(catalogMessages);
    }
}
