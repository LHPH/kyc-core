package com.kyc.core.exception;

import com.kyc.core.enums.MessageType;
import com.kyc.core.model.web.MessageData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static com.kyc.core.constants.GeneralConstants.EXC_ERROR_DATA_LABEL;

@RunWith(MockitoJUnitRunner.class)
public class KycGraphqlExceptionTest {

    @Test
    public void getExtensions_processNotNullMessageData_returnExtensionsNotEmpty(){

        MessageData messageData = new MessageData("CODE","MESSAGE", MessageType.ERROR);
        KycGraphqlException exception = KycGraphqlException.builderGraphqlException()
                .errorData(messageData)
                .build();
        Map<String,Object> extensions = exception.getExtensions();
        Assertions.assertNotNull(exception);
        Assertions.assertEquals("CODE",exception.getExtensions().get("code"));
    }

    @Test
    public void getExtensions_processNullMessageData_returnExtensionsEmpty(){

        MessageData messageData = null;
        KycGraphqlException exception = KycGraphqlException.builderGraphqlException()
                .errorData(messageData)
                .build();
        Map<String,Object> extensions = exception.getExtensions();
        Assertions.assertNotNull(exception);
        Assertions.assertTrue(extensions.isEmpty());
    }

    @Test
    public void toString_processMessageField_returnMessageOnString(){

        MessageData messageData = new MessageData("CODE","MESSAGE", MessageType.ERROR);
        KycGraphqlException exception = KycGraphqlException.builderGraphqlException()
                .errorData(messageData)
                .build();
        String result = exception.toString();
        Assert.assertTrue(result.contains(EXC_ERROR_DATA_LABEL));
    }
}
