package com.kyc.core.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.kyc.core.constants.GeneralConstants.EXC_INPUT_DATA_LABEL;
import static com.kyc.core.constants.GeneralConstants.EXC_OUTPUT_DATA_LABEL;

@RunWith(MockitoJUnitRunner.class)
public class KycExceptionTest {

    @Test
    public void toString_processingFields_returnErrorMessage(){

        KycException exception = KycException.builder()
                .inputData("TEST")
                .outputData("TEST")
                .build();
        String result = exception.toString();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains(EXC_INPUT_DATA_LABEL));
        Assert.assertTrue(result.contains(EXC_OUTPUT_DATA_LABEL));
    }
}
