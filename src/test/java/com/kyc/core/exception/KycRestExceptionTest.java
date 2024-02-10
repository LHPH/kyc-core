package com.kyc.core.exception;

import com.kyc.core.enums.MessageType;
import com.kyc.core.model.MessageData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import static com.kyc.core.constants.GeneralConstants.EXC_ERROR_DATA_LABEL;
import static com.kyc.core.constants.GeneralConstants.EXC_HTTP_STATUS_LABEL;
import static com.kyc.core.constants.GeneralConstants.EXC_INPUT_DATA_LABEL;
import static com.kyc.core.constants.GeneralConstants.EXC_ORIGINAL_EXCEPTION_LABEL;
import static com.kyc.core.constants.GeneralConstants.EXC_OUTPUT_DATA_LABEL;

@RunWith(MockitoJUnitRunner.class)
public class KycRestExceptionTest {

    @Test
    public void toString_partialFields_returnString(){

        KycRestException kycRestException = KycRestException.builderRestException()
                .errorData(new MessageData("CODE","MESSAGE", MessageType.ERROR))
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        String result = kycRestException.toString();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains(EXC_HTTP_STATUS_LABEL));
        Assert.assertTrue(result.contains(EXC_ERROR_DATA_LABEL));
    }

    @Test
    public void toString_allFields_returnString(){
        KycRestException kycRestException = KycRestException.builderRestException()
                .errorData(new MessageData("CODE","MESSAGE", MessageType.ERROR))
                .exception(new NullPointerException("test null"))
                .inputData("INPUT")
                .outputData("OUTPUT")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        String result = kycRestException.toString();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains(EXC_HTTP_STATUS_LABEL));
        Assert.assertTrue(result.contains(EXC_ERROR_DATA_LABEL));
        Assert.assertTrue(result.contains(EXC_INPUT_DATA_LABEL));
        Assert.assertTrue(result.contains(EXC_OUTPUT_DATA_LABEL));
        Assert.assertTrue(result.contains(EXC_ORIGINAL_EXCEPTION_LABEL));
    }
}
